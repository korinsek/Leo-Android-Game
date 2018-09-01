package com.mag.denis.game.ui.main.view.flow.interactionview.levels

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.Action
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.blocks.actionview.ActionImageView
import com.mag.denis.game.ui.main.view.flow.interactionview.AbsFlowView
import kotlinx.android.synthetic.main.partial_flow_action2.view.*

class FlowView2 : AbsFlowView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_flow_action2, this)
    }

    override fun setupViews(fragmentManager: FragmentManager, availableCommands: List<Int>) {
        super.onAttachedToWindow()

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

        listOf(llActionHolder1, llActionHolder2, llActionHolder3, llActionHolder4, llActionHolder5, llActionHolder6).forEach {
            it.setOnDragListener { v, event ->
                getDragListener(v, event)
            }
        }
    }

    override fun getActions(): ArrayList<Command> {
        val list = ArrayList<Command>()

        listOf(llActionHolder1, llActionHolder2, llActionHolder3, llActionHolder4, llActionHolder5, llActionHolder6).forEach {
            val action = it.getChildAt(0)
            if (action is ActionImageView) {
                list.add(Action(action.type))
            }
        }

        return list
    }
}
