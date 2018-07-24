package com.mag.denis.game.ui.main

import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.main.model.Command


class MainPresenterImpl(private val view: MainView, private val levelManager: LevelManager) : MainPresenter {

    override fun onStartClick(commands: ArrayList<Command>) {
        view.doActionsInGame(commands)
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }

    override fun onLevelFinished() {
        if (levelManager.isGameFinished()) {
            view.openScoreScreen()
        } else {
            view.closeOk()
        }
    }

}
