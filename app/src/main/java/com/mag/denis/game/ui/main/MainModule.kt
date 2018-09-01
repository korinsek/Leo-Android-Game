package com.mag.denis.game.ui.main

import com.mag.denis.game.ui.main.view.GameViewSubComponent
import dagger.Binds
import dagger.Module

@Module(subcomponents = [GameViewSubComponent::class])
interface MainModule {

    @Binds fun bindView(activity: MainActivity): MainView

    @Binds fun bindPresenter(presenterImpl: MainPresenterImpl): MainPresenter
}
