package com.mag.denis.game.ui.main

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.MessageDialog
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.*
import com.mag.denis.game.ui.menu.MenuActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), MainView, GameView.OnMessageCallback {

    @Inject lateinit var presenter: MainPresenter

    private var messageDialog: MessageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionUp = ActionImageView(this, R.drawable.ic_arrow_upward, ACTION_UP)
        val actionRight = ActionImageView(this, R.drawable.ic_arrow_right, ACTION_RIGHT)
        val actionLeft = ActionImageView(this, R.drawable.ic_arrow_left, ACTION_LEFT)
        val actionDown = ActionImageView(this, R.drawable.ic_arrow_down, ACTION_DOWN)


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
        val loop1 = LoopView(this)
        val loop2 = LoopView(this)
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

        val if1 = ConditionView(this, supportFragmentManager)
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


        llActionHolder.setOnDragListener { v, event ->
            getDragListener(v, event)
        }

        btStart.setOnClickListener {
            gameView.resetGame()
            presenter.onStartClick(llActionHolder)
        }
        btnMenu.setOnClickListener { presenter.onMenuClick() }

        gameView.setOnMessageCallback(this)
        presenter.onCreate(llActions.childCount)
    }

    override fun doActionsInGame(actions: List<Command>) {
        gameView.doActions(actions)
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

    override fun onResume() {
        super.onResume()
        gameView.invalidate()
    }

    override fun openMenuActivity() {
        val intent = MenuActivity.newIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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

    override fun showGameMessage(messageId: Int) {
        showMessageDialog(messageId)
    }

    override fun showMessageDialog(@StringRes messageId: Int) {
        messageDialog = MessageDialog.show(supportFragmentManager, messageId)
    }

    companion object {
        const val ACTION_UP = "action_up"
        const val ACTION_DOWN = "action_down"
        const val ACTION_RIGHT = "action_right"
        const val ACTION_LEFT = "action_left"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}
