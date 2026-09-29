package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.MissionResultEntity
import com.example.data.model.RoleplaySessionEntity
import com.example.data.model.UserAchievementEntity
import com.example.data.model.UserEntity
import com.example.data.model.UserProfileEntity
import com.example.data.model.VocabularyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Long): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE role = 'STUDENT'")
    fun getAllStudents(): Flow<List<UserEntity>>
}

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profiles WHERE userId = :userId LIMIT 1")
    fun getProfile(userId: Long): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profiles")
    fun getAllProfiles(): Flow<List<UserProfileEntity>>

    @Query("UPDATE user_profiles SET xp = xp + :xpDelta WHERE userId = :userId")
    suspend fun addXp(userId: Long, xpDelta: Int)

    @Query("UPDATE user_profiles SET streak = :newStreak, lastActiveDate = :date WHERE userId = :userId")
    suspend fun updateStreak(userId: Long, newStreak: Int, date: String)

    @Query("""
        UPDATE user_profiles 
        SET listeningMastery = :listening,
            speakingMastery = :speaking,
            readingMastery = :reading,
            writingMastery = :writing,
            vocabularyMastery = :vocabulary,
            grammarMastery = :grammar
        WHERE userId = :userId
    """)
    suspend fun updateSkills(
        userId: Long,
        listening: Int,
        speaking: Int,
        reading: Int,
        writing: Int,
        vocabulary: Int,
        grammar: Int
    )
}

@Dao
interface MissionResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: MissionResultEntity): Long

    @Query("SELECT * FROM mission_results WHERE userId = :userId ORDER BY completedAt DESC")
    fun getResultsForUser(userId: Long): Flow<List<MissionResultEntity>>

    @Query("SELECT DISTINCT missionId FROM mission_results WHERE userId = :userId")
    fun getCompletedMissionIds(userId: Long): Flow<List<String>>

    @Query("SELECT * FROM mission_results")
    fun getAllResults(): Flow<List<MissionResultEntity>>

    @Query("SELECT * FROM mission_results WHERE missionId = :missionId AND userId = :userId ORDER BY score DESC LIMIT 1")
    suspend fun getBestResult(userId: Long, missionId: String): MissionResultEntity?
}

@Dao
interface VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vocabList: List<VocabularyEntity>)

    @Query("SELECT * FROM vocabulary ORDER BY wordAr ASC")
    fun getAllVocabulary(): Flow<List<VocabularyEntity>>

    @Query("SELECT * FROM vocabulary WHERE isSaved = 1 ORDER BY wordAr ASC")
    fun getSavedVocabulary(): Flow<List<VocabularyEntity>>

    @Query("SELECT * FROM vocabulary WHERE mastery < 60 ORDER BY mastery ASC")
    fun getWeakVocabulary(): Flow<List<VocabularyEntity>>

    @Query("UPDATE vocabulary SET isSaved = NOT isSaved WHERE id = :id")
    suspend fun toggleSaved(id: String)

    @Query("UPDATE vocabulary SET mastery = :mastery, reviewCount = reviewCount + 1, lastReviewed = :timestamp WHERE id = :id")
    suspend fun updateMastery(id: String, mastery: Int, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE vocabulary SET mistakesCount = mistakesCount + 1, mastery = MAX(0, mastery - 10) WHERE id = :id")
    suspend fun recordMistake(id: String)
}

@Dao
interface UserAchievementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun unlockAchievement(achievement: UserAchievementEntity)

    @Query("SELECT achievementId FROM user_achievements WHERE userId = :userId")
    fun getEarnedAchievementIds(userId: Long): Flow<List<String>>
}

@Dao
interface RoleplayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: RoleplaySessionEntity)

    @Query("SELECT * FROM roleplay_sessions WHERE userId = :userId ORDER BY createdAt DESC")
    fun getSessionsForUser(userId: Long): Flow<List<RoleplaySessionEntity>>
}
