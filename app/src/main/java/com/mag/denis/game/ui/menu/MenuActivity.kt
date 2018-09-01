package com.mag.denis.game.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.mag.denis.game.R
import com.mag.denis.game.service.MusicService
import com.mag.denis.game.ui.BaseActivity
import com.mag.denis.game.ui.about.AboutActivity
import com.mag.denis.game.ui.level.LevelActivity
import com.mag.denis.game.ui.score.ScoreActivity
import com.mag.denis.game.ui.settings.SettingsActivity
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
        startActivity(LevelActivity.newIntent(this))
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
        presenter.onResume()
        super.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun startMusicService() {
        ActivityCompat.startForegroundService(this, MusicService.newIntent(this))
    }

    override fun stopMusicService() {
        stopService(MusicService.newIntent(this))
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MenuActivity::class.java)
        }
    }
}
