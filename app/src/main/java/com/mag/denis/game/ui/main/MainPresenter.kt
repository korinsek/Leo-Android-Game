package com.mag.denis.game.ui.main

import android.widget.LinearLayout

interface MainPresenter {
    fun onCreate(placeHoldersCount: Int)
    fun onStartClick(holder: LinearLayout)
    fun onMenuClick()
    fun addAction(actionType: String, position: Int)
}
