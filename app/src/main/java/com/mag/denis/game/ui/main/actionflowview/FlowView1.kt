package com.mag.denis.game.ui.main.actionflowview

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
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.Action
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.PlaceholderView
import com.mag.denis.game.ui.main.view.action.ActionImageView
import kotlinx.android.synthetic.main.partial_flow_action1.view.*

class FlowView1(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    init {
        inflate(context, R.layout.partial_flow_action1, this)
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
        llActionHolder4.setOnDragListener { v, event ->
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
//                val owner = draggedView.parent as ViewGroup
//                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit

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
