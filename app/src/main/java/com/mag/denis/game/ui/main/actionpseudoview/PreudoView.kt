package com.mag.denis.game.ui.main.actionpseudoview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.model.Command

class PreudoView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {
    init {
        inflate(context, R.layout.partial_pseudo_view, this)
    }

    fun setupViews(fragmentManager: FragmentManager) {
        super.onAttachedToWindow()

    }

    fun getActions(): ArrayList<Command> {
        return arrayListOf()
    }
}
