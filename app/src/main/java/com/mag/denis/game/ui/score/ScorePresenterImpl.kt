package com.mag.denis.game.ui.score

class ScorePresenterImpl(private val view: ScoreView) : ScorePresenter {
    override fun onBackClicked() {
        view.closeView()
    }
}
