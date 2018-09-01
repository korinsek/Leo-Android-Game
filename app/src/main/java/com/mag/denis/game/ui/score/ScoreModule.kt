package com.mag.denis.game.ui.score

import dagger.Binds
import dagger.Module

@Module
interface ScoreModule {

    @Binds fun bindView(activity: ScoreActivity): ScoreView

    @Binds fun bindPresenter(presenterImpl: ScorePresenterImpl): ScorePresenter
}

