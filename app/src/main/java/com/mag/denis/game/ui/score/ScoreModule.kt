package com.mag.denis.game.ui.score

import android.content.Context
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.manager.ScoreManager
import com.mag.denis.game.ui.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ScoreModule.ScoreBindModule::class))
class ScoreModule {

    @Provides @ActivityScope
    fun providePresenter(context: Context, view: ScoreView, levelManager: LevelManager, scoreManager: ScoreManager): ScorePresenter {
        return ScorePresenterImpl(context, view, levelManager, scoreManager)
    }

    @Module
    interface ScoreBindModule {

        @Binds fun bindView(activity: ScoreActivity): ScoreView

    }

}
