package com.mag.denis.game.ui.main

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.MenuDialog
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.widget.LinearLayout
import android.view.ViewGroup




class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject lateinit var presenter: MainPresenter

    private var menuDialog: MenuDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        btnMenu.setOnClickListener { showMenuDialog() }
        btnDrag1.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        btnDrag2.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        btnDrag3.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        btnDropTarget.setOnDragListener { v, event ->

            val draggedView = event.localState as View
            when (event.action) {
                DragEvent.ACTION_DROP ->{
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
            true

        }

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

                v.visibility = View.INVISIBLE
                true
            }
            event.action == MotionEvent.ACTION_UP -> {
                v.visibility = View.VISIBLE
                true
            }
            else -> false
        }
    }

    private fun showMenuDialog() {
        menuDialog = MenuDialog.show(supportFragmentManager)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}
