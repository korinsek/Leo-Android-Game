package com.mag.denis.game.ui.menu

import com.mag.denis.game.ui.ActivityScope
import javax.inject.Inject

@ActivityScope
class MenuPresenterImpl @Inject constructor(private val view: MenuView) : MenuPresenter {

    override fun onResume() {
        view.startMusicService()
    }

    override fun onPause() {
        view.stopMusicService()
    }

    override fun onPlayClick() {
        view.openGameView()
    }

    override fun onScoreClick() {
        view.openScoreView()
    }

    override fun onSettingsClick() {
        view.openSettingsView()
    }

    override fun onAboutClick() {
        view.openAboutView()
    }
}
