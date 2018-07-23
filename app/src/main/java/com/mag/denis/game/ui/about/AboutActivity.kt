package com.mag.denis.game.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mag.denis.game.R
import com.mag.denis.game.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*
import javax.inject.Inject

class AboutActivity : BaseActivity(), AboutView {

    @Inject lateinit var presenter: AboutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        btBack.setOnClickListener { presenter.onBackClicked() }
    }

    override fun closeView() {
        finish()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }

    }

}
