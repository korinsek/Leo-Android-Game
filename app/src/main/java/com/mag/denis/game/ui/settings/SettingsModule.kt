package com.mag.denis.game.ui.settings

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(SettingsModule.SettingsBindModule::class))
class SettingsModule {

    @Provides @ActivityScope fun providePresenter(view: SettingsView): SettingsPresenter {
        return SettingsPresenterImpl(view)
    }

    @Module
    interface SettingsBindModule {

        @Binds fun bindView(activity: SettingsActivity): SettingsView

    }

}
