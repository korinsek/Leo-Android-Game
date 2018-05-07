package com.mag.denis.game.ui.menu

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(MenuModule.MenuBindModule::class))
class MenuModule {

    @Provides @ActivityScope fun providePresenter(view: MenuView): MenuPresenter {
        return MenuPresenterImpl(view)
    }

    @Module
    interface MenuBindModule {

        @Binds fun bindView(activity: MenuActivity): MenuView

    }

}
