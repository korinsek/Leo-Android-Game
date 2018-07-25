package com.mag.denis.game.ui.main.view.action

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.LinearLayout
import com.mag.denis.game.R
import kotlinx.android.synthetic.main.loop.view.*

class LoopView(context: Context) : ConstraintLayout(context), ActionView {

    init {
        inflate(context, R.layout.loop, this)
    }

    fun getPlaceholder(): LinearLayout {
        return llLoopPlaceholder
    }

    fun setValue(value: Int) {
        etLoopValue.setText(value.toString())
    }
}
