package com.mag.denis.game.ui.menu

import dagger.Binds
import dagger.Module

@Module
interface MenuModule {

    @Binds fun bindView(activity: MenuActivity): MenuView

    @Binds fun bindPresenter(presenterImpl: MenuPresenterImpl): MenuPresenter
}

