package com.mag.denis.game.ui.main.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.LinearLayout
import com.mag.denis.game.R
import kotlinx.android.synthetic.main.condition.view.*

class ConditionView(context: Context) : ConstraintLayout(context), ActionView {

    init {
        inflate(context, R.layout.condition, this);
    }

    fun getTruePlaceholder(): LinearLayout {
        return llIfTruePlaceholder
    }

    fun getFalsePlaceholder(): LinearLayout {
        return llIfFalsePlaceholder
    }
}
