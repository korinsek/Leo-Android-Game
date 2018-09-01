package com.mag.denis.game.ui.menu

interface MenuView {
    fun openGameView()
    fun openScoreView()
    fun openSettingsView()
    fun openAboutView()
    fun startMusicService()
    fun stopMusicService()
}
