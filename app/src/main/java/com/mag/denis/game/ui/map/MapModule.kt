package com.mag.denis.game.ui.map

import dagger.Binds
import dagger.Module

@Module
interface MapModule {

    @Binds fun bindView(activity: MapActivity): MapView

    @Binds fun bindPresenter(presenterImpl: MapPresenterImpl): MapPresenter
}
