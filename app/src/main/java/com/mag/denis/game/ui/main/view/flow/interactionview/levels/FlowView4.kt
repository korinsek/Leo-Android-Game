package com.mag.denis.game.ui.main.view.flow.interactionview.levels

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.view.blocks.actionview.ActionImageView
import com.mag.denis.game.ui.main.view.flow.interactionview.AbsFlowView
import kotlinx.android.synthetic.main.flow_condition.view.*
import kotlinx.android.synthetic.main.partial_flow_action4.view.*

class FlowView4 : AbsFlowView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_flow_action4, this)
    }

    override fun setupViews(fragmentManager: FragmentManager, availableCommands: List<Int>) {
        super.onAttachedToWindow()

        if (availableCommands.contains(LevelManager.COMMAND_ACTIONS)) {
            val actionUp = ActionImageView(context, R.drawable.ic_arrow_upward, MainActivity.ACTION_UP)
            val actionRight = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT)
            val actionLeft = ActionImageView(context, R.drawable.ic_arrow_left, MainActivity.ACTION_LEFT)
            val actionDown = ActionImageView(context, R.drawable.ic_arrow_down, MainActivity.ACTION_DOWN)

            listOf(actionUp, actionRight, actionLeft, actionDown).forEach {
                llActions.addView(it)
                it.setOnTouchListener { v, event ->
                    getTouchListener(v, event)
                }
            }

            llActions.setOnDragListener { v, event ->
                getDragListener(v, event)
            }
        }

        listOf(llActionHolder1, llActionHolder2, flowConditionView1).forEach {
            it.setOnDragListener { v, event ->
                getDragListener(v, event)
            }
        }
    }

    override fun getActions(): ArrayList<Command> {
        val list = ArrayList<Command>()

        val action1 = llActionHolder1.getChildAt(0) as ActionImageView
        val firstAction = Action(action1.type)
        list.add(firstAction)


        val action2 = llActionHolder2.getChildAt(0) as ActionImageView
        val secondAction = Action(action2.type)
        list.add(secondAction)


        val condition = etIfValue.text?.toString()?.toIntOrNull() ?: 1
        for (i in 1..7) {
            list.add(IfCondition(ColorCondition(condition, Condition.TYPE_TRUE), listOf(firstAction, secondAction), emptyList()))
        }

        return list
    }
}
