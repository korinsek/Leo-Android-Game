package com.mag.denis.game.ui.finish

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(FinishModule.FinishBindModule::class))
class FinishModule {

    @Provides @ActivityScope fun providePresenter(view: FinishView): FinishPresenter {
        return FinishPresenterImpl(view)
    }

    @Module
    interface FinishBindModule {

        @Binds fun bindView(activity: FinishActivity): FinishView

    }

}
