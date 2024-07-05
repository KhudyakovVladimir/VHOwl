package com.khudyakovvladimir.vhowl.app

import android.app.Application
import android.content.Context
import com.khudyakovvladimir.vhowl.di.AppComponent
import com.khudyakovvladimir.vhowl.di.DaggerAppComponent
import com.khudyakovvladimir.vhowl.game.Data
import java.util.concurrent.TimeUnit

class GameApp: Application() {

    lateinit var appComponent: AppComponent
    private set

    lateinit var data: Data
    private set

    override fun onCreate() {
        super.onCreate()

        TimeUnit.SECONDS.sleep(2)

        data = Data()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is GameApp -> appComponent
        else -> applicationContext.appComponent
    }

val Context.data: Data
    get() = when(this) {
        is GameApp -> data
        else -> applicationContext.data
    }

