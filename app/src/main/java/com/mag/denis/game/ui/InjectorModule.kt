package com.mag.denis.game.ui

import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.MainModule
import com.mag.denis.game.ui.settings.SettingsActivity
import com.mag.denis.game.ui.settings.SettingsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class InjectorModule {

    @ActivityScope @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivityInjector(): MainActivity

    @ActivityScope @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeSettingsActivityInjector(): SettingsActivity
}
