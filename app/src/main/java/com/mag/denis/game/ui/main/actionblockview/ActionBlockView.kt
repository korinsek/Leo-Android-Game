package com.mag.denis.game.ui.main.actionblockview

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
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.view.PlaceholderView
import com.mag.denis.game.ui.main.view.action.ActionImageView
import com.mag.denis.game.ui.main.view.action.ConditionView
import com.mag.denis.game.ui.main.view.action.LoopView
import kotlinx.android.synthetic.main.partial_flow_action1.view.*

class ActionBlockView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_action_view, this)
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val paddingPx = resources.getDimensionPixelSize(R.dimen.actionPadding)
        this.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
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

        //Loop
        val loop1 = LoopView(context)
        val loop2 = LoopView(context)
        llActions.addView(loop1)
//        llActions.addView(loop2)

        loop1.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        loop1.setOnDragListener { v, event ->
            getDragListener(v, event)
        }

        loop2.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        loop2.setOnDragListener { v, event ->
            getDragListener(v, event)
        }

        val if1 = ConditionView(context, fragmentManager)
        llActions.addView(if1)

        if1.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        if1.getTruePlaceholder().setOnDragListener { v, event ->
            getDragListener(v, event)
        }
        if1.getFalsePlaceholder().setOnDragListener { v, event ->
            getDragListener(v, event)
        }


        llActionHolder1.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
    }

    private fun getTouchListener(v: View, event: MotionEvent): Boolean {
        return when {
            event.action == MotionEvent.ACTION_DOWN -> {
                v.visibility = View.GONE
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

    private fun getDragListener(v: View, e: DragEvent): Boolean {
        if (e.action == DragEvent.ACTION_DROP || e.action == DragEvent.ACTION_DRAG_ENDED) {
            when (e.localState) {
                is ActionImageView ->
                    when (v) {
                        is PlaceholderView -> handleActionToPlaceholderDrag(v, e)
                        is LoopView -> handleActionToLoopDrag(v, e)
                        is LinearLayout -> handleActionToConditionDrag(v, e)
                    }
                is LoopView -> when (v) {
                    is PlaceholderView -> handleLoopToPlaceholderDrag(v, e)
                    is LoopView -> handleLoopToLoopDrag(v, e)
                    is LinearLayout -> handleLoopToConditionDrag(v, e)
                }
                is ConditionView -> when (v) {
                    is PlaceholderView -> handleConditionToPlaceholderDrag(v, e)
                    is LoopView -> handleConditionToLoopDrag(v, e)
                    is LinearLayout -> handleConditionToConditionDrag(v, e)
                }
            }
        }
        return true
    }

    private fun handleActionToPlaceholderDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ActionImageView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit
                val container = v as PlaceholderView
                container.addView(draggedView)
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleActionToConditionDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ActionImageView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView)
                val container = v as LinearLayout
                container.addView(draggedView)
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleLoopToConditionDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as LoopView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView)
                val container = v as LinearLayout
                container.addView(draggedView)
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleConditionToConditionDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ConditionView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val container = v as LinearLayout
                if (draggedView != container.parent) {
                    val owner = draggedView.parent as ViewGroup
                    owner.removeView(draggedView)
                    container.addView(draggedView)
                }
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }


    private fun handleLoopToPlaceholderDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as LoopView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit
                val container = v as PlaceholderView
                container.addView(draggedView)
                //TODO ADD action to presenter
//                presenter.addAction(draggedView.type, container.position)

                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleConditionToPlaceholderDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ConditionView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit
                val container = v as PlaceholderView
                container.addView(draggedView)

                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleActionToLoopDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ActionImageView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit, v nasem primeru ga pustimo tam
                val container = v as LoopView
                container.getPlaceholder().addView(draggedView)
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleLoopToLoopDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as LoopView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val container = v as LoopView
                if (draggedView != container) {
                    val owner = draggedView.parent as ViewGroup
                    owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit
                    container.getPlaceholder().addView(draggedView)
                }
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleConditionToLoopDrag(v: View, e: DragEvent) {
        val draggedView = e.localState as ConditionView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit, v nasem primeru ga pustimo tam
                val container = v as LoopView
                container.getPlaceholder().addView(draggedView)
                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    fun getActions(): ArrayList<Command> {
        return getAllChildren(llActionHolder1)
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

}
