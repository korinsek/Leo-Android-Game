package com.mag.denis.game.ui.level

interface LevelPresenter {
    fun onCreate()
    fun onResume()
    fun onPause()
    fun onLevelClicked(level: Int)
    fun onBackClicked()
    fun onNextClick()
    fun onPrevClick()
    fun onLevelComplete()
    fun onDestroy()
}
