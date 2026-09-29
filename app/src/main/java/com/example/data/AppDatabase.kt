package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.MissionResultDao
import com.example.data.dao.RoleplayDao
import com.example.data.dao.UserAchievementDao
import com.example.data.dao.UserDao
import com.example.data.dao.UserProfileDao
import com.example.data.dao.VocabularyDao
import com.example.data.model.MissionResultEntity
import com.example.data.model.RoleplaySessionEntity
import com.example.data.model.UserAchievementEntity
import com.example.data.model.UserEntity
import com.example.data.model.UserProfileEntity
import com.example.data.model.VocabularyEntity

@Database(
    entities = [
        UserEntity::class,
        UserProfileEntity::class,
        MissionResultEntity::class,
        VocabularyEntity::class,
        UserAchievementEntity::class,
        RoleplaySessionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun missionResultDao(): MissionResultDao
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun userAchievementDao(): UserAchievementDao
    abstract fun roleplayDao(): RoleplayDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arabiyyun_hayati.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
