package com.mag.denis.game.ui.map

interface MapPresenter {
    fun onCreate()
    fun onLevelClicked(level: Int)
    fun onBackClicked()
    fun onNextClick()
    fun onPrevClick()
    fun onLevelComplete()
}
