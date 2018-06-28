package com.mag.denis.game.ui.main.actionview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R

class FlowView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    init {
        inflate(context, R.layout.action, this)
    }
    fun setupViews(fragmentManager: FragmentManager) {
        super.onAttachedToWindow()

    }

}
