package com.mag.denis.game.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject

class MapActivity : DaggerAppCompatActivity(), MapView {

    @Inject lateinit var presenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        tvLevel1.setOnClickListener { presenter.onLevelClicked() }
        btBack.setOnClickListener { presenter.onBackClicked() }
    }

    override fun openGameView() {
        startActivity(MainActivity.newIntent(this))
    }

    override fun closeView() {
        finish()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }

    }

}
