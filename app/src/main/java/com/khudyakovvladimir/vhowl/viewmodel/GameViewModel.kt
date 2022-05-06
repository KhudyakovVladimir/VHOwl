package com.khudyakovvladimir.vhowl.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.khudyakovvladimir.vhowl.database.HighScore
import com.khudyakovvladimir.vhowl.database.HighScoreDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameViewModel @Inject constructor(
    application: Application,
    val highScoreDao: HighScoreDao
) : AndroidViewModel(application) {

    private var listOfHighScore: LiveData<List<HighScore>>? = highScoreDao.getAllHighScoreAsLiveData()

    fun getListOfHighScore(): LiveData<List<HighScore>>? {
        return listOfHighScore
    }

    fun getListOfHighScoreFromDB(): LiveData<List<HighScore>>? {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                listOfHighScore = highScoreDao.getAllHighScoreAsLiveData()
            }
            job.join()
        }
        return listOfHighScore
    }

}