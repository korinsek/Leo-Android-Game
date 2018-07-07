package com.mag.denis.game.ui.main

import com.mag.denis.game.ui.main.model.*


class MainPresenterImpl(private val view: MainView) : MainPresenter {

    override fun onStartClick(commands:ArrayList<Command>) {
        view.doActionsInGame(commands)
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }
}
