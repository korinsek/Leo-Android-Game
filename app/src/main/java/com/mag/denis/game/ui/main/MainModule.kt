package com.mag.denis.game.ui.main

import dagger.Binds
import dagger.Module

@Module
interface MainModule {

    @Binds fun bindView(activity: MainActivity): MainView

    @Binds fun bindPresenter(presenterImpl: MainPresenterImpl): MainPresenter
}
