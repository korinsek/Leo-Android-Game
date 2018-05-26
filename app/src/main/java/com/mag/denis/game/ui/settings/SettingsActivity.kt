package com.mag.denis.game.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mag.denis.game.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : DaggerAppCompatActivity(), SettingsView {

    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        btBack.setOnClickListener { presenter.onBackClicked() }
    }

    override fun closeView() {
        finish()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }

    }

}
