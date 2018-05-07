package com.mag.denis.game.ui.score

import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ScoreModule.ScoreBindModule::class))
class ScoreModule {

    @Provides @ActivityScope fun providePresenter(view: ScoreView): ScorePresenter {
        return ScorePresenterImpl(view)
    }

    @Module
    interface ScoreBindModule {

        @Binds fun bindView(activity: ScoreActivity): ScoreView

    }

}
