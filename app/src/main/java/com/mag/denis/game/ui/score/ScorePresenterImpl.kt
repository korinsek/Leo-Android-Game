package com.mag.denis.game.ui.score

import android.content.Context
import com.mag.denis.game.R
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.manager.ScoreManager

class ScorePresenterImpl(private val context: Context, private val view: ScoreView, private val levelManager: LevelManager, private val scoreManager: ScoreManager) : ScorePresenter {

    override fun onCreate() {
        val scores = scoreManager.getScores()
        var scoreStr = ""
        if (scores.isNotEmpty()) {
            for ((i, score) in scores.withIndex()) {
                scoreStr += "${i + 1}. $score points"
            }
            view.showScores(scoreStr)
        } else {
            view.showScoreMessage(R.string.score_message_no_scores)
        }

        val starsSum = levelManager.getSumStars()
        view.showStars(starsSum)
    }

    override fun onBackClicked() {
        view.closeView()
    }

    override fun onFinish() {
        view.showFinished()
    }
}
