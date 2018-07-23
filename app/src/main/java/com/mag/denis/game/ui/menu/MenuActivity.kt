package com.mag.denis.game.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.mag.denis.game.R
import com.mag.denis.game.service.MusicService
import com.mag.denis.game.ui.BaseActivity
import com.mag.denis.game.ui.about.AboutActivity
import com.mag.denis.game.ui.map.MapActivity
import com.mag.denis.game.ui.score.ScoreActivity
import com.mag.denis.game.ui.settings.SettingsActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import javax.inject.Inject


class MenuActivity : BaseActivity(), MenuView {

    @Inject lateinit var presenter: MenuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btPlay.setOnClickListener { presenter.onPlayClick() }
        btScore.setOnClickListener { presenter.onScoreClick() }
        btSettings.setOnClickListener { presenter.onSettingsClick() }
        btAbout.setOnClickListener { presenter.onAboutClick() }
    }

    override fun openGameView() {
        startActivity(MapActivity.newIntent(this))
    }

    override fun openScoreView() {
        startActivity(ScoreActivity.newIntent(this))
    }

    override fun openSettingsView() {
        startActivity(SettingsActivity.newIntent(this))
    }

    override fun openAboutView() {
        startActivity(AboutActivity.newIntent(this))
    }

    override fun onResume() {
        ActivityCompat.startForegroundService(this, MusicService.newIntent(this))
        super.onResume()
    }

    override fun onPause() {
        stopService(MusicService.newIntent(this))
        super.onPause()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MenuActivity::class.java)
        }
    }

}
