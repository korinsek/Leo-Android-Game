package com.mag.denis.game.ui.main.view.action

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.widget.LinearLayout
import com.mag.denis.game.R

class ActionImageView(context: Context, val drawableId: Int, val type: String,
        val backgroundId: Int = R.drawable.bg_action, val viewWidth: Int = LinearLayout.LayoutParams.MATCH_PARENT) : AppCompatImageView(context), ActionView {

    init {
        setImageResource(drawableId)
        setBackgroundResource(backgroundId)
        val height = resources.getDimensionPixelSize(R.dimen.actionHeight)
        this.layoutParams = LinearLayout.LayoutParams(viewWidth, height)
    }
}
