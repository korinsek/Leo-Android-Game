package com.mag.denis.game.ui.score

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mag.denis.game.R
import com.mag.denis.game.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_score.*
import javax.inject.Inject

class ScoreActivity : BaseActivity(), ScoreView {

    @Inject lateinit var presenter: ScorePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        btBack.setOnClickListener { presenter.onBackClicked() }

        val finished = intent?.getBooleanExtra(EXTRA_FINISH, false) ?: false
        if (finished) {
            presenter.onFinish()
        }

        presenter.onCreate()
    }

    override fun closeView() {
        finish()
    }

    override fun showFinished() {
        tvTitle.setText(R.string.score_title_finished)
    }

    override fun showStars(stars: Int) {
        tvStarsSum.text = getString(R.string.score_current_points, stars)
    }

    override fun showScores(scores: String) {
        tvScores.text = scores
    }

    override fun showScoreMessage(messageId: Int) {
        tvScores.setText(R.string.score_message_no_scores)
    }

    companion object {
        private const val EXTRA_FINISH = "com.mag.denis.game.ui.score.ScoreActivity.finish"

        fun newIntent(context: Context, showFinished: Boolean = false): Intent {
            val intent = Intent(context, ScoreActivity::class.java)
            intent.putExtra(EXTRA_FINISH, showFinished)
            return intent
        }
    }
}
