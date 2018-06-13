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
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.MessageDialog
import com.mag.denis.game.ui.main.view.ActionImageView
import com.mag.denis.game.ui.main.view.GameView
import com.mag.denis.game.ui.main.view.LoopView
import com.mag.denis.game.ui.main.view.PlaceholderView
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
        llActions.addView(loop2)

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

        //Placeholders
        val placeholderView1 = PlaceholderView(this, 1)
        val placeholderView2 = PlaceholderView(this, 2)
        val placeholderView3 = PlaceholderView(this, 3)
        val placeholderView4 = PlaceholderView(this, 4)

        placeholderView1.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
        placeholderView2.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
        placeholderView3.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
        placeholderView4.setOnDragListener { v, event ->
            getDragListener(v, event)
        }
        llActionHolders.addView(placeholderView1)
        llActionHolders.addView(placeholderView2)
        llActionHolders.addView(placeholderView3)
        llActionHolders.addView(placeholderView4)



        btStart.setOnClickListener { presenter.onStartClick() }
        btnMenu.setOnClickListener { presenter.onMenuClick() }

        gameView.setOnMessageCallback(this)
        presenter.onCreate(llActions.childCount)
    }

    override fun doActionsInGame(actions: List<String>) {
        gameView.doActions(actions)
    }

    private fun getTouchListener(v: View, event: MotionEvent): Boolean {
        return when {
            event.action == MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    v.startDrag(data, shadowBuilder, v, 0)
                }

//                v.visibility = View.INVISIBLE
                true
            }
            event.action == MotionEvent.ACTION_UP -> {
//                v.visibility = View.VISIBLE
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

    private fun getDragListener(v: View, event: DragEvent): Boolean {
        if (event.action == DragEvent.ACTION_DROP || event.action == DragEvent.ACTION_DRAG_ENDED) {
            when (event.localState) {
                is ActionImageView ->
                    when (v) {
                        is PlaceholderView -> handleActionToPlaceholderDrag(v, event)
                        is LoopView -> handleActionToLoopDrag(v, event)
                    }
                is LoopView -> when (v) {
                    is PlaceholderView -> handleLoopToPlaceholderDrag (v, event)
                    is LoopView -> handleLoopToLoopDrag(v, event)
                }
            }
        }
        return true
    }

    private fun handleActionToPlaceholderDrag(v: View, event: DragEvent) {
        val draggedView = event.localState as ActionImageView
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit
                val container = v as PlaceholderView
//                val newImageView = ActionImageView(this, draggedView.drawableId, draggedView.type)
//                newImageView.setOnClickListener {
//                    (newImageView.parent as ViewGroup).removeAllViews() //TODO remove action from presenter
//                }
//                container.removeAllViews()// Da se ne stackajo eden pod drugim, pobrisemo prejsnjega
                container.addView(draggedView)
                //TODO ADD action to presenter
                presenter.addAction(draggedView.type, container.position)

                draggedView.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggedView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleLoopToPlaceholderDrag(v: View, event: DragEvent) {
        val draggedView = event.localState as LoopView
        when (event.action) {
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

    private fun handleActionToLoopDrag(v: View, event: DragEvent) {
        val draggedView = event.localState as ActionImageView
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit, v nasem primeru ga pustimo tam
                val container = v as LoopView
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

    private fun handleLoopToLoopDrag(v: View, event: DragEvent) {
        val draggedView = event.localState as LoopView
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val owner = draggedView.parent as ViewGroup
                owner.removeView(draggedView) //odstrani view ce ga hocemo prestavit
                val container = v as LoopView
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
