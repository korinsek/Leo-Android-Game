package com.mag.denis.game.ui.about

import dagger.Binds
import dagger.Module

@Module
interface AboutModule {

    @Binds fun bindView(activity: AboutActivity): AboutView

    @Binds fun bindPresenter(presenterImpl: AboutPresenterImpl): AboutPresenter
}
