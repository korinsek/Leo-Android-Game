package com.mag.denis.game.ui.menu

class MenuPresenterImpl(private val view: MenuView) : MenuPresenter {

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
