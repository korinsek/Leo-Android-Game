package com.mag.denis.game.ui.score

interface ScoreView {
    fun closeView()
    fun showFinished()
    fun showStars(stars: Int)
    fun showScores(scores: String)
}
