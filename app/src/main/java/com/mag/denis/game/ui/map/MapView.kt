package com.mag.denis.game.ui.map

import android.support.annotation.StringRes

interface MapView {
    fun setupLevel(level: Int, enabled: Boolean, stars: Int = 0, animate:Boolean = false)
    fun openGameView()
    fun closeView()
    fun enableNext(enabled: Boolean)
    fun enablePrev(enabled: Boolean)
    fun setStageTitle(@StringRes titleId: Int)
    fun animateLevels()
    fun clearLevels()
}
