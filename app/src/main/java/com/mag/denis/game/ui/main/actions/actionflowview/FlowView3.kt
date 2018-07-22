package com.mag.denis.game.ui.main.actions.actionflowview

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.view.action.ActionImageView
import kotlinx.android.synthetic.main.flow_condition.view.*
import kotlinx.android.synthetic.main.partial_flow_action3.view.*

class FlowView3 : AbsFlowView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_flow_action3, this)
    }

    override fun setupViews(fragmentManager: FragmentManager, avilableCommands: List<Int>) {
        super.onAttachedToWindow()

        if (avilableCommands.contains(LevelManager.COMMAND_ACTIONS)) {
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

        llActionHolder1.setOnDragListener { v, event ->
            getDragListener(v, event)
        }


        llActionHolder2.setOnDragListener { v, event ->
            getDragListener(v, event)
        }


        flowConditionView1.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
    }

    override fun getActions(): ArrayList<Command> {
        //TODO check placeholders if all filled
        val list = ArrayList<Command>()

        val action1 = llActionHolder1.getChildAt(0) as ActionImageView
        val firstAction = Action(action1.type)
        list.add(firstAction)

        val condition = etIfValue.text?.toString()?.toIntOrNull() ?: 1
        for (i in 1..7) {
            list.add(IfCondition(ColorCondition(condition, Condition.TYPE_TRUE), listOf(firstAction), emptyList()))
        }

        val action2 = llActionHolder2.getChildAt(0) as ActionImageView
        val secondAction = Action(action2.type)
        list.add(secondAction)


        return list
    }
}
