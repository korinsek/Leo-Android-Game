package com.mag.denis.game.ui.main

import android.support.annotation.StringRes

interface MainView {
    fun doActionsInGame(actions: List<String>)
    fun openMenuActivity()
    fun showMessageDialog(@StringRes messageId: Int)
}
