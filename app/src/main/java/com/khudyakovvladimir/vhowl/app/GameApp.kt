package com.khudyakovvladimir.vhowl.app

import android.app.Application
import android.content.Context
import com.khudyakovvladimir.vhowl.di.AppComponent
import com.khudyakovvladimir.vhowl.di.DaggerAppComponent
import java.util.concurrent.TimeUnit

class GameApp: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        TimeUnit.SECONDS.sleep(2)

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