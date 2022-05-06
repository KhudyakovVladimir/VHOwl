package com.khudyakovvladimir.vhowl.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HighScore::class], exportSchema = false, version = 1)
abstract class HighScoreDatabase: RoomDatabase() {

    abstract fun highScoreDao(): HighScoreDao

    companion object {
        private const val HIGH_SCORE_DB = "high_score_db"
        var instance: HighScoreDatabase? = null

        fun getInstance(context: Context): HighScoreDatabase? {
            if(instance != null) {
                synchronized(this) {
                    instance =
                        Room
                            .databaseBuilder(context, HighScoreDatabase::class.java, HIGH_SCORE_DB)
                            .build()
                }
            }
            return instance
        }
    }
}