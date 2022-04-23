package com.khudyakovvladimir.vhowl.di

import android.app.Application
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.view.GameFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [MainModule::class])
interface AppComponent {

    fun injectGameFragment(gameFragment: GameFragment)

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
}