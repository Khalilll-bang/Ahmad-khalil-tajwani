package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val email: String,
    val passwordHash: String,
    val role: String, // "STUDENT" or "TEACHER"
    val arabicLevel: String, // "BEGINNER" or "INTERMEDIATE"
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val userId: Long,
    val avatar: String = "🧑‍🎓",
    val arabicLevel: String = "BEGINNER",
    val xp: Int = 0,
    val currentLevel: Int = 1,
    val streak: Int = 1,
    val longestStreak: Int = 1,
    val lastActiveDate: String = "",
    val listeningMastery: Int = 60,
    val speakingMastery: Int = 50,
    val readingMastery: Int = 65,
    val writingMastery: Int = 55,
    val vocabularyMastery: Int = 60,
    val grammarMastery: Int = 58
)

@Entity(tableName = "mission_results")
data class MissionResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val missionId: String,
    val score: Int,
    val xpEarned: Int,
    val attempts: Int = 1,
    val timeSpentSec: Int = 0,
    val completedAt: Long = System.currentTimeMillis(),
    val speakingScore: Int = 0,
    val listeningScore: Int = 0,
    val readingScore: Int = 0,
    val writingScore: Int = 0,
    val vocabularyScore: Int = 0,
    val grammarScore: Int = 0
)

@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey val id: String, // e.g. "voc_bab"
    val wordAr: String,
    val meaningId: String,
    val tashkeel: String,
    val exampleAr: String,
    val exampleId: String,
    val category: String, // "house", "food", "directions", etc.
    val mastery: Int = 50, // 0-100 (0-39: Need Review, 40-69: Learning, 70-89: Good, 90-100: Mastered)
    val reviewCount: Int = 0,
    val mistakesCount: Int = 0,
    val isSaved: Boolean = false,
    val lastReviewed: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_achievements")
data class UserAchievementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val achievementId: String,
    val earnedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "roleplay_sessions")
data class RoleplaySessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val missionId: String,
    val speakerId: String,
    val conversationTranscript: String,
    val score: Int,
    val createdAt: Long = System.currentTimeMillis()
)

// Non-entity domain models for static simulation content
data class LocationModel(
    val id: String,
    val levelNumber: Int,
    val nameAr: String,
    val nameId: String,
    val icon: String,
    val description: String,
    val orderNumber: Int
)

data class MissionModel(
    val id: String,
    val locationId: String,
    val titleAr: String,
    val titleId: String,
    val missionType: String, // "RECOGNIZE", "USE", "PROBLEM_SOLVING"
    val difficulty: String, // "Easy", "Medium", "Hard"
    val objective: String,
    val xp: Int,
    val orderNumber: Int,
    val characterSpeakerId: String,
    val characterNameAr: String,
    val activities: List<ActivityModel>
)

data class ActivityModel(
    val id: String,
    val activityType: String, // "MULTIPLE_CHOICE", "SENTENCE_BUILDER", "LISTENING", "SPEAKING", "DECISION_BRANCH", "TYPING"
    val speakerId: String = "teacher_male_01",
    val promptAr: String,
    val promptId: String,
    val audioTextAr: String = "",
    val speakerNameAr: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String,
    val wordChips: List<String> = emptyList(), // for sentence builder
    val hint1: String, // Light clue
    val hint2: String, // Vocabulary clue
    val hint3: String, // Sentence structure clue
    val explanationAr: String = "",
    val explanationId: String = ""
)

data class AchievementModel(
    val id: String,
    val nameAr: String,
    val nameId: String,
    val description: String,
    val icon: String,
    val requiredMissionId: String? = null,
    val requiredXp: Int = 0
)

data class SpeakerProfile(
    val speakerId: String,
    val nameAr: String,
    val roleTitle: String,
    val gender: String, // "MALE", "FEMALE"
    val pitch: Float = 1.0f,
    val speechRate: Float = 1.0f,
    val style: String
)
