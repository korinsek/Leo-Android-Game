package com.mag.denis.game.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mag.denis.game.R
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), MapView {

    @Inject lateinit var presenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }

    }

}
