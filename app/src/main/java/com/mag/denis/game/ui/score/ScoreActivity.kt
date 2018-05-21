package com.mag.denis.game.ui.score

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mag.denis.game.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : DaggerAppCompatActivity(), ScoreView {

    @Inject lateinit var presenter: ScorePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ScoreActivity::class.java)
        }

    }

}
