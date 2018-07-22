package com.mag.denis.game.ui.map

import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LevelManager

class MapPresenterImpl(private val view: MapView, private val gameManager: GameManager, private val levelManager: LevelManager) : MapPresenter {

    private var currentStage = 0
    private var maxStage = 0
    private lateinit var stages: ArrayList<String>

    override fun onCreate() {
        stages = gameManager.getStages()
        if (gameManager.getCurrentStage() != stages[currentStage]) {
            currentStage = stages.indexOf(gameManager.getCurrentStage())
            gameManager.setCurrentStage(stages[currentStage])
        }
        view.setStageTitle(stages[currentStage])
        maxStage = stages.size - 1
        checkButtons()
        setupLevels()
    }

    override fun onLevelClicked(level: Int) {
        gameManager.setCurrentLevel(level)
        view.openGameView()
    }

    override fun onBackClicked() {
        view.closeView()
    }

    override fun onNextClick() {
        if (currentStage < maxStage) {
            currentStage++
        }
        gameManager.setCurrentStage(stages[currentStage])
        checkButtons()
        view.setStageTitle(stages[currentStage])
        setupLevels()
        view.animateLevels()
    }

    override fun onPrevClick() {
        if (currentStage > 0) {
            currentStage--
        }
        gameManager.setCurrentStage(stages[currentStage])
        checkButtons()
        view.setStageTitle(stages[currentStage])
        setupLevels()
        view.animateLevels()
    }

    override fun onLevelComplete() {
        val currentLevel = gameManager.getCurrentLevel()
        //TODO check and move to next stage
        if (currentLevel < 8) {
            gameManager.setMaxLevelAchived(currentLevel + 1)
            setupLevels()
        }
    }

    private fun checkButtons() {
        view.enablePrev(currentStage > 0)
        view.enableNext(currentStage < maxStage)
    }

    private fun setupLevels() {
        view.clearLevels()
        for (i in 1..levelManager.numOfLevels) {
            view.setupLevel(i, i <= gameManager.getMaxLevelAchived(), levelManager.getStarsForLevel(i), i == gameManager.getMaxLevelAchived()) //TODO get stars for level from history
        }
    }

    override fun onDestroy() {
        levelManager.saveScores()
    }
}
