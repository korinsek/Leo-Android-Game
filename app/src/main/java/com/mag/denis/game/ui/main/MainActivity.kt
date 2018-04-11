package com.mag.denis.game.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.MenuDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject lateinit var presenter: MainPresenter

    private var menuDialog: MenuDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMenu.setOnClickListener { showMenuDialog() }

    }

    private fun showMenuDialog() {
        menuDialog = MenuDialog.show(supportFragmentManager)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}
