package com.khudyakovvladimir.vhowl.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.khudyakovvladimir.vhowl.database.DBHelper
import com.khudyakovvladimir.vhowl.database.HighScoreDatabase
import com.khudyakovvladimir.vhowl.game.GameView
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.view.GameFragment
import com.khudyakovvladimir.vhowl.view.HeroFragment
import com.khudyakovvladimir.vhowl.view.HighScoreFragment
import com.khudyakovvladimir.vhowl.view.StartFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [MainModule::class])
interface AppComponent {

    fun injectGameFragment(gameFragment: GameFragment)
    fun injectStartFragment(startFragment: StartFragment)
    fun injectHighScoreFragment(highScoreFragment: HighScoreFragment)
    fun injectHeroFragment(heroFragment: HeroFragment)
    fun injectGameView(gameView: GameView)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}

@Module
class MainModule {

    @Provides
    fun provideSystemHelper(): SystemHelper {
        return SystemHelper()
    }

    @Provides
    fun provideSoundHelper(application: Application): SoundHelper {
        return SoundHelper(application)
    }

    @Provides
    fun provideDBHelper(context: Context): DBHelper {
        return DBHelper(context)
    }

    @Provides
    fun provideHighScoreDatabase(application: Application) =
        Room
            .databaseBuilder(application.applicationContext, HighScoreDatabase::class.java, "high_score_db")
            .build()

    @Provides
    fun provideHighScoreDao(highScoreDatabase: HighScoreDatabase) = highScoreDatabase.highScoreDao()
}