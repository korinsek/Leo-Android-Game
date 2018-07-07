package com.mag.denis.game.ui.main.view.flow

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.ColorDialog
import kotlinx.android.synthetic.main.flow_condition.view.*


class FlowConditionView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), ColorDialog.ColorCallback {

    init {
        inflate(context, R.layout.flow_condition, this)
        rlIfValue.setOnClickListener {
            ColorDialog.show((context as FragmentActivity).supportFragmentManager).setColorListener(this)
        }
    }

    override fun onColorTypeSelect(colorType: Int, @DrawableRes drawableSelected: Int) {
        etIfValue.setText(colorType.toString())
        rlIfValue.setBackgroundResource(drawableSelected)
    }
}
