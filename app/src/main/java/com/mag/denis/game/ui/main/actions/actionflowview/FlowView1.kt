package com.mag.denis.game.ui.main.actions.actionflowview

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R
import com.mag.denis.game.manager.LevelManager.Companion.COMMAND_ACTIONS
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.Action
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.action.ActionImageView
import kotlinx.android.synthetic.main.partial_flow_action1.view.*

class FlowView1 : AbsFlowView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_flow_action1, this)
    }

    override fun setupViews(fragmentManager: FragmentManager, avilableCommands: List<Int>) {
        super.onAttachedToWindow()

        if (avilableCommands.contains(COMMAND_ACTIONS)) {
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
        llActionHolder4.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
    }

    override fun getActions(): ArrayList<Command> {
        //TODO check placeholders if all filled
        val list = ArrayList<Command>()

        val action1 = llActionHolder1.getChildAt(0)
        if (action1 is ActionImageView) {
            list.add(Action(action1.type))
        }

        val action2 = llActionHolder2.getChildAt(0)
        if (action2 is ActionImageView) {
            list.add(Action(action2.type))
        }

        val action3 = flowConditionView1.getChildAt(0)
        if (action3 is ActionImageView) {
            list.add(Action(action3.type))
        }

        val action4 = flowConditionView1.getChildAt(0)
        if (action4 is ActionImageView) {
            list.add(Action(action4.type))
        }

        return list
    }
}
