package com.mag.denis.game.ui.map

interface MapView {
    fun setupLevel(level: Int, enabled: Boolean, stars: Int = 0, animate:Boolean = false)
    fun openGameView()
    fun closeView()
    fun enableNext(enabled: Boolean)
    fun enablePrev(enabled: Boolean)
    fun setStageTitle(title: String)
    fun animateLevels()
    fun clearLevels()
}
