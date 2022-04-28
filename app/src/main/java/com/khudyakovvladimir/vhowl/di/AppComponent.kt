package com.khudyakovvladimir.vhowl.di

import android.app.Application
import com.khudyakovvladimir.vhowl.game.GameView
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.view.GameFragment
import com.khudyakovvladimir.vhowl.view.StartFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [MainModule::class])
interface AppComponent {

    fun injectGameFragment(gameFragment: GameFragment)
    fun injectStartFragment(startFragment: StartFragment)
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
}