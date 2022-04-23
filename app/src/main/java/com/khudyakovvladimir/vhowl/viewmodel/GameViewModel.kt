package com.khudyakovvladimir.vhowl.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.khudyakovvladimir.vhowl.game.Cell
import javax.inject.Inject

class GameViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private var listOfCell: MutableLiveData<Cell> = MutableLiveData<Cell>()

    fun getListCells(): MutableLiveData<Cell> {
        Log.d("TAG", "NewsViewModel - getListCells()")
        return listOfCell
    }
}