package com.mag.denis.game.ui.main

import android.support.annotation.StringRes
import com.mag.denis.game.ui.main.model.Command

interface MainView {
    fun doActionsInGame(actions: List<Command>)
    fun openMenuActivity()
    fun showMessageDialog(@StringRes messageId: Int)
}
