package com.mag.denis.game.ui.about

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(AboutModule.AboutBindModule::class))
class AboutModule {

    @Provides @ActivityScope fun providePresenter(view: AboutView): AboutPresenter {
        return AboutPresenterImpl(view)
    }

    @Module
    interface AboutBindModule {

        @Binds fun bindView(activity: AboutActivity): AboutView

    }

}
