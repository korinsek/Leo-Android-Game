package com.mag.denis.game.ui.main.flowview

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.view.PlaceholderView
import com.mag.denis.game.ui.main.view.action.ActionImageView
import com.mag.denis.game.ui.main.view.flow.FlowConditionView
import kotlinx.android.synthetic.main.flow_condition.view.*
import kotlinx.android.synthetic.main.partial_flow_action2.view.*

class FlowView2(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    init {
        inflate(context, R.layout.partial_flow_action2, this)
    }

    fun setupViews(fragmentManager: FragmentManager) {
        super.onAttachedToWindow()

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

    private fun getDragListener(v: View, e: DragEvent): Boolean {
        if (e.action == DragEvent.ACTION_DROP || e.action == DragEvent.ACTION_DRAG_ENDED) {
            when (e.localState) {
                is ActionImageView -> handleActionToPlaceholderDrag(v, e)
            }
        }
        return true
    }

    private fun handleActionToPlaceholderDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ActionImageView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val newImageView = ActionImageView(context, draggedView.drawableId, draggedView.type)
                newImageView.setOnClickListener {
                    (newImageView.parent as ViewGroup).removeAllViews()
                }

                val container = v as PlaceholderView
                container.removeAllViews()
                container.addView(newImageView)
                draggedView.visibility = View.VISIBLE
                draggedView.alpha = 1f
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun getTouchListener(v: View, event: MotionEvent): Boolean {
        return when {
            event.action == MotionEvent.ACTION_DOWN -> {
                v.visibility = View.VISIBLE
                v.alpha = 0.5f
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    v.startDrag(data, shadowBuilder, v, 0)
                }
                true
            }
            event.action == MotionEvent.ACTION_UP -> {
                true
            }
            else -> false
        }
    }

    fun getActions(): ArrayList<Command> {
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
