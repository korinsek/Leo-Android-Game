package com.mag.denis.game.ui.settings

import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LangManager
import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(SettingsModule.SettingsBindModule::class))
class SettingsModule {

    @Provides @ActivityScope
    fun providePresenter(view: SettingsView, gameManager: GameManager, langManager: LangManager): SettingsPresenter {
        return SettingsPresenterImpl(view, gameManager, langManager)
    }

    @Module
    interface SettingsBindModule {

        @Binds fun bindView(activity: SettingsActivity): SettingsView

    }

}
