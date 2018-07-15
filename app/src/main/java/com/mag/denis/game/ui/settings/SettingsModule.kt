package com.mag.denis.game.ui.settings

import android.content.SharedPreferences
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(SettingsModule.SettingsBindModule::class))
class SettingsModule {

    @Provides @ActivityScope
    fun providePresenter(view: SettingsView, sharedPreferences: SharedPreferences, gameManager: GameManager): SettingsPresenter {
        return SettingsPresenterImpl(view, sharedPreferences, gameManager)
    }

    @Module
    interface SettingsBindModule {

        @Binds fun bindView(activity: SettingsActivity): SettingsView

    }

}
