package com.mag.denis.game.ui.settings

import dagger.Binds
import dagger.Module

@Module
interface SettingsModule {

    @Binds fun bindView(activity: SettingsActivity): SettingsView

    @Binds fun bindPresenter(presenterImpl: SettingsPresenterImpl): SettingsPresenter
}

