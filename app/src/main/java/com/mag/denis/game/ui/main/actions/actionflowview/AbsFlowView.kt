package com.mag.denis.game.ui.main.actions.actionflowview

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
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.PlaceholderView
import com.mag.denis.game.ui.main.view.action.ActionImageView
import kotlinx.android.synthetic.main.partial_flow_action1.view.*

abstract class AbsFlowView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val paddingPx = resources.getDimensionPixelSize(R.dimen.actionPadding)
        this.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
    }

    internal fun getDragListener(v: View, e: DragEvent): Boolean {
        if (e.action == DragEvent.ACTION_DROP || e.action == DragEvent.ACTION_DRAG_ENDED) {
            when (e.localState) {
                is ActionImageView -> handleActionToPlaceholderDrag(v, e)
            }
        }
        return true
    }

    private fun handleActionToPlaceholderDrag(view: View, e: DragEvent) {
        val draggedView = e.localState as ActionImageView
        when (e.action) {
            DragEvent.ACTION_DROP -> {
                if (view.parent != llActions) {
                    val owner = draggedView.parent as ViewGroup
                    if (owner != llActions) {
                        owner.removeView(draggedView)
                    }

                    val newImageView = ActionImageView(context, draggedView.drawableId, draggedView.type)
                    newImageView.setOnTouchListener { v, event ->
                        getTouchListener(v, event)
                    }
                    val container = view as PlaceholderView
                    if (container != llActions) {
                        container.addView(newImageView)
                    }
                }
                draggedView.visibility = View.VISIBLE
                draggedView.alpha = 1f
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    internal fun getTouchListener(v: View, event: MotionEvent): Boolean {
        return when {
            event.action == MotionEvent.ACTION_DOWN -> {
                v.visibility = View.VISIBLE
                v.alpha = 0.5f
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    @Suppress("DEPRECATION")
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

    abstract fun setupViews(fragmentManager: FragmentManager, avilableCommands: List<Int>)

    abstract fun getActions(): ArrayList<Command>
}
