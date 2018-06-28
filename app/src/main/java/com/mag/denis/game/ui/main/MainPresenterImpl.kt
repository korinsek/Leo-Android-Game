package com.mag.denis.game.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.view.ActionImageView
import com.mag.denis.game.ui.main.view.ConditionView
import com.mag.denis.game.ui.main.view.LoopView


class MainPresenterImpl(private val view: MainView) : MainPresenter {

    override fun onStartClick(holder: LinearLayout) {
        val list = getAllChildren(holder)
        view.doActionsInGame(list)
    }

    private fun getAllChildren(v: View): ArrayList<Command> {
        val viewGroup = v as ViewGroup

        val list = ArrayList<Command>()

        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)

            if (child is ActionImageView) {
                list.add(Action(child.type))
            } else if (child is LoopView) {
                val repeatEt = child.findViewById<EditText>(R.id.etLoopValue)
                val containterll = child.findViewById<LinearLayout>(R.id.llLoopPlaceholder)
                val value = if (!repeatEt.text.isNullOrEmpty()) {
                    repeatEt.text.toString().toInt()
                } else {
                    0
                }

                list.add(Loop(value, getAllChildren(containterll)))
            } else if (child is ConditionView) {
                val condition = child.findViewById<EditText>(R.id.etIfValue)?.text?.toString()?.toIntOrNull() ?: 1
                val containterTrue = child.findViewById<LinearLayout>(R.id.llIfTruePlaceholder)
                val containterFalse = child.findViewById<LinearLayout>(R.id.llIfFalsePlaceholder)
                list.add(IfCondition(ColorCondition(condition, Condition.TYPE_TRUE), getAllChildren(containterTrue), getAllChildren(containterFalse)))
            } else {
                return getAllChildren(child)
            }

        }
        return list
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }
}
