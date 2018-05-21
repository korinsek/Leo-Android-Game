package com.mag.denis.game.ui.main.view

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import com.mag.denis.game.R

class PlaceholderView(context: Context, val position: Int) : LinearLayout(context) {

    init {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        setBackgroundResource(R.drawable.bg_action_placeholder)
    }
}
