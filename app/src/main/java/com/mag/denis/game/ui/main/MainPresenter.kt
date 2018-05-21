package com.mag.denis.game.ui.main

interface MainPresenter {
    fun onCreate(placeHoldersCount: Int)
    fun onStartClick()
    fun onMenuClick()
    fun addAction(actionType: String, position: Int)
}
