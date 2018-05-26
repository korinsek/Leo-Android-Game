package com.mag.denis.game.ui.finish

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mag.denis.game.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FinishActivity : DaggerAppCompatActivity(), FinishView {

    @Inject lateinit var presenter: FinishPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FinishActivity::class.java)
        }

    }

}
