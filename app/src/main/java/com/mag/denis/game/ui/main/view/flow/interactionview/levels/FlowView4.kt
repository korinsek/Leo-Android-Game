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

    override fun setupViews(fragmentManager: FragmentManager, avilableCommands: List<Int>) {
        super.onAttachedToWindow()

        if (avilableCommands.contains(LevelManager.COMMAND_ACTIONS)) {
            val actionUp = ActionImageView(context, R.drawable.ic_arrow_upward, MainActivity.ACTION_UP)
            val actionRight = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT)
            val actionLeft = ActionImageView(context, R.drawable.ic_arrow_left, MainActivity.ACTION_LEFT)
            val actionDown = ActionImageView(context, R.drawable.ic_arrow_down, MainActivity.ACTION_DOWN)


            llActions.addView(actionUp)
            llActions.addView(actionRight)
            llActions.addView(actionLeft)
            llActions.addView(actionDown)

            llActions.setOnDragListener { v, event ->
                getDragListener(v, event)
            }

            actionUp.setOnTouchListener { v, event ->
                getTouchListener(v, event)
            }
            actionRight.setOnTouchListener { v, event ->
                getTouchListener(v, event)
            }
            actionLeft.setOnTouchListener { v, event ->
                getTouchListener(v, event)
            }
            actionDown.setOnTouchListener { v, event ->
                getTouchListener(v, event)
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
