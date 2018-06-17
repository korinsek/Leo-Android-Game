package com.mag.denis.game.ui.main.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.LinearLayout
import com.mag.denis.game.R
import kotlinx.android.synthetic.main.loop.view.*

class LoopView(context: Context) : ConstraintLayout(context) {

    init {
        inflate(context, R.layout.loop, this);
    }

    fun getPlaceholder(): LinearLayout {
        return llLoopPlaceholder
    }
}
