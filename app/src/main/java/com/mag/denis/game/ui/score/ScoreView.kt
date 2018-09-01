package com.mag.denis.game.ui.score

import android.support.annotation.StringRes

interface ScoreView {
    fun closeView()
    fun showFinished()
    fun showStars(stars: Int)
    fun showScores(scores: String)
    fun showScoreMessage(@StringRes messageId: Int)
}
