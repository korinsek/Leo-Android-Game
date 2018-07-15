package com.mag.denis.game.ui.map

import com.mag.denis.game.manager.GameManager

class MapPresenterImpl(private val view: MapView, private val gameManager: GameManager) : MapPresenter {

    private var currentStage = 0
    private var maxStage = 0
    private lateinit var stages: ArrayList<String>

    override fun onCreate() {
        stages = gameManager.getStages()
        maxStage = stages.size - 1
        checkButtons()
        for (i in 1..8) {
            view.setupLevel(i, i <= gameManager.getMaxLevelAchived(), 0) //TODO get stars for level from history
        }
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
        checkButtons()
        view.setStageTitle(stages[currentStage])
        view.animateLevels()
    }

    override fun onPrevClick() {
        if (currentStage > 0) {
            currentStage--
        }
        checkButtons()
        view.setStageTitle(stages[currentStage])
        view.animateLevels()
    }

    private fun checkButtons() {
        view.enablePrev(currentStage > 0)
        view.enableNext(currentStage < maxStage)
    }
}
