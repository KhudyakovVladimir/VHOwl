package com.khudyakovvladimir.vhowl.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.lang.IllegalArgumentException

class GameViewModelFactory @AssistedInject constructor(
    @Assisted("application")
    var application: Application
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED CAST")
            return GameViewModel(
                application = application
            ) as T
        }
        throw IllegalArgumentException("Unable to construct GameViewModel")
    }

    @AssistedFactory
    interface Factory {
        fun createGameViewModelFactory(@Assisted("application") application: Application): GameViewModelFactory
    }

}