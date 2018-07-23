package com.mag.denis.game.ui.main.actions.actionflowview

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import com.mag.denis.game.R
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

        listOf(llActionHolder1, llActionHolder2, llActionHolder3, llActionHolder4, llActionHolder5).forEach {
            it.setOnDragListener { v, event ->
                getDragListener(v, event)
            }
        }
    }

    override fun getActions(): ArrayList<Command> {
        val list = ArrayList<Command>()

                val views = listOf(llActionHolder1, llActionHolder2, llActionHolder3, llActionHolder4, llActionHolder5)
        views.forEach {
            val action = it.getChildAt(0)
            if (action is ActionImageView) {
                list.add(Action(action.type))
            }
        }

        if(list.size<views.size){
            throw IllegalStateException("Fill all flow chart fields")
        }

        return list
    }
}
