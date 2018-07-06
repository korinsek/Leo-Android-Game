package com.mag.denis.game.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.view.ActionImageView
import com.mag.denis.game.ui.main.view.ConditionView
import com.mag.denis.game.ui.main.view.LoopView


class MainPresenterImpl(private val view: MainView) : MainPresenter {

    override fun onStartClick(commands:ArrayList<Command>) {
        view.doActionsInGame(commands)
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }
}
