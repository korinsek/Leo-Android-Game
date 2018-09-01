package com.mag.denis.game.ui.main.view.blocks.actionview

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.ColorDialog
import kotlinx.android.synthetic.main.condition.view.*

class ConditionView(context: Context, val supportFragmentManager: FragmentManager) : ConstraintLayout(context), ActionView, ColorDialog.ColorCallback {

    init {
        inflate(context, R.layout.condition, this)
        rlIfValue.setOnClickListener {
            ColorDialog.show(supportFragmentManager).setColorListener(this)
        }
    }

    fun getTruePlaceholder(): LinearLayout {
        return llIfTruePlaceholder
    }

    fun getFalsePlaceholder(): LinearLayout {
        return llIfFalsePlaceholder
    }

    override fun onColorTypeSelect(colorType: Int, @DrawableRes drawableSelected: Int) {
        etIfValue.setText(colorType.toString())
        rlIfValue.setBackgroundResource(drawableSelected)
    }
}
