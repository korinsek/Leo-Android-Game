package com.mag.denis.game.ui.main

import com.mag.denis.game.R
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.manager.ScoreManager
import com.mag.denis.game.ui.ActivityScope
import com.mag.denis.game.ui.main.model.Command
import javax.inject.Inject

@ActivityScope
class MainPresenterImpl @Inject constructor(private val view: MainView, private val levelManager: LevelManager,
        private val gameManager: GameManager, private val scoreManager: ScoreManager) : MainPresenter {

    override fun onResume() {
        view.startMusicService()
        view.invalidateGame()
    }

    override fun onPause() {
        view.stopMusicService()
    }

    override fun onStartClick(commands: ArrayList<Command>) {
        view.doActionsInGame(commands)
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }

    override fun onLevelFinished() {
        if (levelManager.isGameFinished()) {
            scoreManager.addScore(levelManager.getSumStars())
            view.openScoreScreen()
        } else if (gameManager.getCurrentLevel() == levelManager.numOfLevelsForStage() && gameManager.getCurrentStage() == GameManager.STAGE_PSEUDO) {
            view.showMessageDialog(R.string.main_end_not_compleated_message)
        } else {
            view.closeOk()
        }
    }

    override fun onHelpClicked() {
        view.openHelpDialog()
    }
}
