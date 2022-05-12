package com.khudyakovvladimir.vhowl.database

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DBHelper(private val context: Context) {

    lateinit var highScoreDatabase: HighScoreDatabase

    fun createDatabase() {
        highScoreDatabase = HighScoreDatabase.getInstance(context)!!

        val sharedPreferences = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        if (sharedPreferences.contains("database")) {
            Log.d("TAG", "isDatabaseCreated = true")
        }else {
            CoroutineScope(Dispatchers.IO).launch {
                highScoreDatabase.highScoreDao().insertHighScore(HighScore(1, "Owl", 0))
            }
        }

    }
}