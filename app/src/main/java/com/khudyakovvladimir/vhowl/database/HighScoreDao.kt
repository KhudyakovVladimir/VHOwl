package com.khudyakovvladimir.vhowl.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.khudyakovvladimir.vhowl.database.HighScore

@Dao
interface HighScoreDao {

    @Query("SELECT * FROM high_score ORDER BY score DESC")
    fun getAllHighScore(): List<HighScore>

    @Query("SELECT * FROM high_score ORDER BY score DESC")
    fun getAllHighScoreAsLiveData(): LiveData<List<HighScore>>?

    @Query("SELECT * FROM high_score WHERE id = :id")
    fun getHighScoreById(id: Int): HighScore

    @Query("DELETE FROM high_score")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighScore(highScore: HighScore)

    @Delete
    fun deleteHighScore(highScore: HighScore)

    @Update
    fun updateHighScore(highScore: HighScore)
}