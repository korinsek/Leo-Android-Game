package com.mag.denis.game.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import javax.inject.Inject

class MenuActivity : DaggerAppCompatActivity(), MenuView {

    @Inject lateinit var presenter: MenuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btPlay.setOnClickListener { presenter.onPlayClick() }
    }

    override fun openGameActivity() {
        startActivity(MainActivity.newIntent(this))
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MenuActivity::class.java)
        }
    }

}
