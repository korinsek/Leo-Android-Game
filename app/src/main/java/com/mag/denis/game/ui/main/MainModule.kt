package com.mag.denis.game.ui.main

import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(MainModule.MainBindModule::class))
class MainModule {

    @Provides @ActivityScope fun providePresenter(view: MainView, levelManager: LevelManager): MainPresenter {
        return MainPresenterImpl(view, levelManager)
    }

    @Module
    interface MainBindModule {

        @Binds fun bindView(activity: MainActivity): MainView

    }

}
