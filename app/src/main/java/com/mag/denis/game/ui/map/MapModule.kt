package com.mag.denis.game.ui.map

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(MapModule.MapBindModule::class))
class MapModule {

    @Provides @ActivityScope fun providePresenter(view: MapView): MapPresenter {
        return MapPresenterImpl(view)
    }

    @Module
    interface MapBindModule {

        @Binds fun bindView(activity: MapActivity): MapView

    }

}
