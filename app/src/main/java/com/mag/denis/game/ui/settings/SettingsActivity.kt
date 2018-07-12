package com.mag.denis.game.ui.settings

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.view.PlaceholderView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : DaggerAppCompatActivity(), SettingsView {

    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        btBack.setOnClickListener { presenter.onBackClicked() }
        btnKotlin.setOnClickListener { presenter.onKotlinClicked() }
        btnPython.setOnClickListener { presenter.onPythonClicked() }
        initReorderThemes()
        presenter.onCreate()
    }

    private fun initReorderThemes() {

        llActions.setOnDragListener { v, event ->
            getDragListener(v, event)
        }

        tvBlocks.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        tvFlow.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
        tvPseudo.setOnTouchListener { v, event ->
            getTouchListener(v, event)
        }
    }

    override fun selectLanguage(isKotlin: Boolean) {
        btnKotlin.isActivated = isKotlin
        btnPython.isActivated = !isKotlin
    }


    private fun getDragListener(v: View, e: DragEvent): Boolean {
        if (e.action == DragEvent.ACTION_DROP || e.action == DragEvent.ACTION_DRAG_ENDED) {
            when (e.localState) {
                is TextView -> {
                    val draggedView = e.localState as TextView
                    when (e.action) {
                        DragEvent.ACTION_DROP -> {
                            val owner = draggedView.parent as ViewGroup
                            owner.removeView(draggedView)
                            val container = v as PlaceholderView
                            container.addView(draggedView)
                            draggedView.visibility = View.VISIBLE
                        }
                        DragEvent.ACTION_DRAG_ENDED -> {
                            draggedView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        return true
    }


    private fun getTouchListener(v: View, event: MotionEvent): Boolean {
        return when {
            event.action == MotionEvent.ACTION_DOWN -> {
                v.visibility = View.VISIBLE
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

    override fun closeView() {
        finish()
    }

    override fun onBackPressed() {
        presenter.onBackClicked()
        super.onBackPressed()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }

    }

}
