package com.mag.denis.game.ui.main

import com.mag.denis.game.ui.main.model.Command

interface MainPresenter {
    fun onResume()
    fun onPause()
    fun onStartClick(commands: ArrayList<Command>)
    fun onMenuClick()
    fun onLevelFinished()
    fun onHelpClicked()
}
