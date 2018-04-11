package com.mag.denis.game.ui.main

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(MainModule.MainBindModule::class))
class MainModule {

    @Provides @ActivityScope fun providePresenter(view: MainView): MainPresenter {
        return MainPresenterImpl(view)
    }

    @Module
    interface MainBindModule {

        @Binds fun bindView(activity: MainActivity): MainView

    }

}
