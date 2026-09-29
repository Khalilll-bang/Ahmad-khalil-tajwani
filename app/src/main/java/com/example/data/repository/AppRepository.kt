package com.example.data.repository

import android.content.Context
import com.example.data.AppDatabase
import com.example.data.model.AchievementModel
import com.example.data.model.LocationModel
import com.example.data.model.MissionModel
import com.example.data.model.MissionResultEntity
import com.example.data.model.RoleplaySessionEntity
import com.example.data.model.UserAchievementEntity
import com.example.data.model.UserEntity
import com.example.data.model.UserProfileEntity
import com.example.data.model.VocabularyEntity
import com.example.data.seed.SeedFinalChallenge
import com.example.data.seed.SeedLocations
import com.example.data.seed.SeedMissionsLevel1
import com.example.data.seed.SeedMissionsLevel2
import com.example.data.seed.SeedMissionsLevel3to5
import com.example.data.seed.SeedVocabulary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppRepository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val userDao = db.userDao()
    private val profileDao = db.userProfileDao()
    private val missionResultDao = db.missionResultDao()
    private val vocabDao = db.vocabularyDao()
    private val achievementDao = db.userAchievementDao()
    private val roleplayDao = db.roleplayDao()

    // In-memory catalog of static simulation content
    val allLocations: List<LocationModel> = SeedLocations.locations
    val allMissions: List<MissionModel> = SeedMissionsLevel1.missions +
            SeedMissionsLevel2.missions +
            SeedMissionsLevel3to5.missions +
            listOf(SeedFinalChallenge.finalChallengeMission)
    val allAchievements: List<AchievementModel> = SeedLocations.achievements

    suspend fun initializeDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        // Seed default vocabulary
        vocabDao.insertAll(SeedVocabulary.initialVocabulary)

        // Seed demo accounts if no user exists
        val demoStudent = userDao.getUserByEmail("ahmad@student.com")
        if (demoStudent == null) {
            val studentId = userDao.insertUser(
                UserEntity(
                    name = "أحمد الإندونيسي",
                    email = "ahmad@student.com",
                    passwordHash = hashPassword("123456"),
                    role = "STUDENT",
                    arabicLevel = "BEGINNER"
                )
            )
            profileDao.insertOrUpdate(
                UserProfileEntity(
                    userId = studentId,
                    avatar = "🧑‍🎓",
                    arabicLevel = "BEGINNER",
                    xp = 180,
                    currentLevel = 1,
                    streak = 4,
                    longestStreak = 7,
                    lastActiveDate = getTodayDate(),
                    listeningMastery = 75,
                    speakingMastery = 65,
                    readingMastery = 80,
                    writingMastery = 70,
                    vocabularyMastery = 75,
                    grammarMastery = 68
                )
            )
            // Pre-seed some achievements and mission results for realistic experience
            missionResultDao.insertResult(
                MissionResultEntity(
                    userId = studentId,
                    missionId = "m_house_1",
                    score = 100,
                    xpEarned = 20,
                    completedAt = System.currentTimeMillis() - 86400000,
                    speakingScore = 90,
                    listeningScore = 95,
                    readingScore = 100,
                    writingScore = 90,
                    vocabularyScore = 100,
                    grammarScore = 90
                )
            )
            missionResultDao.insertResult(
                MissionResultEntity(
                    userId = studentId,
                    missionId = "m_house_2",
                    score = 95,
                    xpEarned = 30,
                    completedAt = System.currentTimeMillis() - 43200000,
                    speakingScore = 85,
                    listeningScore = 90,
                    readingScore = 95,
                    writingScore = 85,
                    vocabularyScore = 90,
                    grammarScore = 85
                )
            )
            achievementDao.unlockAchievement(
                UserAchievementEntity(userId = studentId, achievementId = "ach_key")
            )
        }

        // Demo Teacher Account
        val demoTeacher = userDao.getUserByEmail("ustadz@teacher.com")
        if (demoTeacher == null) {
            val teacherId = userDao.insertUser(
                UserEntity(
                    name = "الأستاذ محمود الشافعي",
                    email = "ustadz@teacher.com",
                    passwordHash = hashPassword("teacher123"),
                    role = "TEACHER",
                    arabicLevel = "ADVANCED"
                )
            )
            profileDao.insertOrUpdate(
                UserProfileEntity(
                    userId = teacherId,
                    avatar = "🧑‍🏫",
                    arabicLevel = "ADVANCED",
                    xp = 1200,
                    currentLevel = 6,
                    streak = 15,
                    longestStreak = 30,
                    lastActiveDate = getTodayDate()
                )
            )
        }
    }

    // ==================== AUTHENTICATION ====================
    suspend fun registerUser(name: String, email: String, password: String, arabicLevel: String, role: String): Result<UserEntity> =
        withContext(Dispatchers.IO) {
            val existing = userDao.getUserByEmail(email.trim().lowercase())
            if (existing != null) {
                return@withContext Result.failure(Exception("البريد الإلكتروني مسجل بالفعل! (Email sudah terdaftar)"))
            }
            val newUser = UserEntity(
                name = name.trim(),
                email = email.trim().lowercase(),
                passwordHash = hashPassword(password),
                role = role,
                arabicLevel = arabicLevel
            )
            val id = userDao.insertUser(newUser)
            val userWithId = newUser.copy(id = id)

            profileDao.insertOrUpdate(
                UserProfileEntity(
                    userId = id,
                    avatar = if (role == "TEACHER") "🧑‍🏫" else "🧑‍🎓",
                    arabicLevel = arabicLevel,
                    xp = 0,
                    currentLevel = 1,
                    streak = 1,
                    lastActiveDate = getTodayDate()
                )
            )
            Result.success(userWithId)
        }

    suspend fun loginUser(email: String, password: String): Result<UserEntity> = withContext(Dispatchers.IO) {
        val user = userDao.getUserByEmail(email.trim().lowercase())
            ?: return@withContext Result.failure(Exception("المستخدم غير موجود (Pengguna tidak ditemukan)"))
        if (user.passwordHash != hashPassword(password)) {
            return@withContext Result.failure(Exception("كلمة المرور غير صحيحة (Password salah)"))
        }
        // Update streak if needed
        val today = getTodayDate()
        val profile = profileDao.getProfile(user.id).first()
        if (profile != null && profile.lastActiveDate != today) {
            profileDao.updateStreak(user.id, profile.streak + 1, today)
        }
        Result.success(user)
    }

    fun getUser(userId: Long): Flow<UserEntity?> = userDao.getUserById(userId)
    fun getUserProfile(userId: Long): Flow<UserProfileEntity?> = profileDao.getProfile(userId)
    fun getAllStudents(): Flow<List<UserEntity>> = userDao.getAllStudents()
    fun getAllProfiles(): Flow<List<UserProfileEntity>> = profileDao.getAllProfiles()
    fun getAllResults(): Flow<List<MissionResultEntity>> = missionResultDao.getAllResults()

    // ==================== PROGRESS & MISSIONS ====================
    fun getCompletedMissionIds(userId: Long): Flow<List<String>> =
        missionResultDao.getCompletedMissionIds(userId)

    fun getMissionResults(userId: Long): Flow<List<MissionResultEntity>> =
        missionResultDao.getResultsForUser(userId)

    suspend fun completeMission(
        userId: Long,
        missionId: String,
        score: Int,
        xpEarned: Int,
        speakingScore: Int,
        listeningScore: Int,
        readingScore: Int,
        writingScore: Int,
        vocabularyScore: Int,
        grammarScore: Int
    ) = withContext(Dispatchers.IO) {
        val prevBest = missionResultDao.getBestResult(userId, missionId)
        val isFirstCompletion = prevBest == null

        val result = MissionResultEntity(
            userId = userId,
            missionId = missionId,
            score = score,
            xpEarned = if (isFirstCompletion) xpEarned else (xpEarned / 3), // replay reward
            completedAt = System.currentTimeMillis(),
            speakingScore = speakingScore,
            listeningScore = listeningScore,
            readingScore = readingScore,
            writingScore = writingScore,
            vocabularyScore = vocabularyScore,
            grammarScore = grammarScore
        )
        missionResultDao.insertResult(result)

        // Award XP
        val awardedXp = if (isFirstCompletion) xpEarned else (xpEarned / 3)
        profileDao.addXp(userId, awardedXp)

        // Update skill proficiencies
        val profile = profileDao.getProfile(userId).first()
        if (profile != null) {
            val newListening = ((profile.listeningMastery * 3) + listeningScore) / 4
            val newSpeaking = ((profile.speakingMastery * 3) + speakingScore) / 4
            val newReading = ((profile.readingMastery * 3) + readingScore) / 4
            val newWriting = ((profile.writingMastery * 3) + writingScore) / 4
            val newVocab = ((profile.vocabularyMastery * 3) + vocabularyScore) / 4
            val newGrammar = ((profile.grammarMastery * 3) + grammarScore) / 4
            profileDao.updateSkills(
                userId,
                newListening.coerceIn(0, 100),
                newSpeaking.coerceIn(0, 100),
                newReading.coerceIn(0, 100),
                newWriting.coerceIn(0, 100),
                newVocab.coerceIn(0, 100),
                newGrammar.coerceIn(0, 100)
            )
        }

        // Check and unlock corresponding achievement
        val matchingAch = allAchievements.find { it.requiredMissionId == missionId }
        if (matchingAch != null) {
            achievementDao.unlockAchievement(
                UserAchievementEntity(userId = userId, achievementId = matchingAch.id)
            )
        }
    }

    // ==================== VOCABULARY ====================
    fun getAllVocabulary(): Flow<List<VocabularyEntity>> = vocabDao.getAllVocabulary()
    fun getSavedVocabulary(): Flow<List<VocabularyEntity>> = vocabDao.getSavedVocabulary()
    fun getWeakVocabulary(): Flow<List<VocabularyEntity>> = vocabDao.getWeakVocabulary()

    suspend fun toggleVocabSaved(vocabId: String) = withContext(Dispatchers.IO) {
        vocabDao.toggleSaved(vocabId)
    }

    suspend fun recordVocabMistake(vocabId: String) = withContext(Dispatchers.IO) {
        vocabDao.recordMistake(vocabId)
    }

    suspend fun updateVocabMastery(vocabId: String, newMastery: Int) = withContext(Dispatchers.IO) {
        vocabDao.updateMastery(vocabId, newMastery.coerceIn(0, 100))
    }

    // ==================== ACHIEVEMENTS ====================
    fun getUserEarnedAchievements(userId: Long): Flow<List<String>> =
        achievementDao.getEarnedAchievementIds(userId)

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
}
