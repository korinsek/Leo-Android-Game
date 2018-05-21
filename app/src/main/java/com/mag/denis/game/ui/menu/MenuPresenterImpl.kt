package com.mag.denis.game.ui.menu

class MenuPresenterImpl(private val view: MenuView) : MenuPresenter {
    override fun onPlayClick() {
        view.openGameActivity()
    }
}
