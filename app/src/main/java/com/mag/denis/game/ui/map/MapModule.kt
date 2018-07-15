package com.mag.denis.game.ui.map

import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(MapModule.MapBindModule::class))
class MapModule {

    @Provides @ActivityScope fun providePresenter(view: MapView, gameManager: GameManager): MapPresenter {
        return MapPresenterImpl(view, gameManager)
    }

    @Module
    interface MapBindModule {

        @Binds fun bindView(activity: MapActivity): MapView

    }

}
