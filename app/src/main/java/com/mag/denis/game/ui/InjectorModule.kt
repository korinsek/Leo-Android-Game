package com.mag.denis.game.ui

import com.mag.denis.game.service.MusicModule
import com.mag.denis.game.service.MusicService
import com.mag.denis.game.ui.about.AboutActivity
import com.mag.denis.game.ui.about.AboutModule
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.MainModule
import com.mag.denis.game.ui.level.LevelActivity
import com.mag.denis.game.ui.level.LevelModule
import com.mag.denis.game.ui.menu.MenuActivity
import com.mag.denis.game.ui.menu.MenuModule
import com.mag.denis.game.ui.score.ScoreActivity
import com.mag.denis.game.ui.score.ScoreModule
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

    @ActivityScope @ContributesAndroidInjector(modules = [LevelModule::class])
    abstract fun contributeMapActivityInjector(): LevelActivity

    @ActivityScope @ContributesAndroidInjector(modules = [MenuModule::class])
    abstract fun contributeMenuActivityInjector(): MenuActivity

    @ActivityScope @ContributesAndroidInjector(modules = [ScoreModule::class])
    abstract fun contributeScoreActivityInjector(): ScoreActivity

    @ActivityScope @ContributesAndroidInjector(modules = [AboutModule::class])
    abstract fun contributeAboutActivityInjector(): AboutActivity

    @ServiceScope @ContributesAndroidInjector(modules = [MusicModule::class])
    abstract fun contributeMusicServiceInjector(): MusicService
}
