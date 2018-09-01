package com.mag.denis.game.ui.main

import com.mag.denis.game.R
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.main.model.Command


class MainPresenterImpl(private val view: MainView, private val levelManager: LevelManager, private val gameManager: GameManager) : MainPresenter {

    override fun onStartClick(commands: ArrayList<Command>) {
        view.doActionsInGame(commands)
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }

    override fun onLevelFinished() {
        if (levelManager.isGameFinished()) {
            view.openScoreScreen()
        } else if (gameManager.getCurrentLevel() == levelManager.numOfLevelsForStage() && gameManager.getCurrentStage() == GameManager.STAGE_PSEUDO) {
            view.showMessageDialog(R.string.main_end_not_compleated_message)
        } else {
            view.closeOk()
        }
    }

}
