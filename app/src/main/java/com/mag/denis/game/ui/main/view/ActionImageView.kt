package com.mag.denis.game.ui.main.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.widget.LinearLayout
import com.mag.denis.game.R

class ActionImageView(context: Context, val drawableId: Int, val type: String,
        val backgroundId: Int = R.drawable.bg_action) : AppCompatImageView(context) {

    init {
        setImageResource(drawableId)
        setBackgroundResource(backgroundId)
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}
