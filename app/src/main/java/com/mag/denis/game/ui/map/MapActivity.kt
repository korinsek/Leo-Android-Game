package com.mag.denis.game.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject
import android.animation.ObjectAnimator




class MapActivity : DaggerAppCompatActivity(), MapView {

    @Inject lateinit var presenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        btBack.setOnClickListener { presenter.onBackClicked() }
        ibNext.setOnClickListener {
            presenter.onNextClick()
        }
        ibPrevious.setOnClickListener {
            presenter.onPrevClick()
        }
        presenter.onCreate()
    }

    override fun openGameView() {
        startActivity(MainActivity.newIntent(this))
    }

    override fun closeView() {
        finish()
    }

    override fun enableNext(enabled: Boolean) {
        ibNext.visibility = if (enabled) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    override fun enablePrev(enabled: Boolean) {
        ibPrevious.visibility = if (enabled) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    override fun setStageTitle(title: String) {
        tvStage.text = title
    }

    override fun animateLevels() {
        glLevels.visibility = View.GONE
        TransitionManager.beginDelayedTransition(glLevels)
        glLevels.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(glLevels)
    }

    override fun setupLevel(level: Int, enabled: Boolean, stars: Int) {
        val tv = Button(this)
        tv.setBackgroundResource(R.drawable.bg_placeholder_table)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(10, 0, 10, 10)
        tv.layoutParams = params
        tv.id = level
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tv.text = level.toString()
        tv.gravity = Gravity.CENTER
        tv.setOnClickListener {
            presenter.onLevelClicked(level)
        }
        val drawable = when (stars) {
            0 -> R.drawable.ic_star_0
            1 -> R.drawable.ic_star_1
            2 -> R.drawable.ic_star_2
            3 -> R.drawable.ic_star_3
            else -> throw IllegalStateException("Drawable for so many stars not supported")
        }
        val img = ContextCompat.getDrawable(this, drawable)
        img?.setBounds(0, 0, 150, 80)
        tv.setCompoundDrawables(null, null, null, img)
        tv.isEnabled = enabled
        glLevels.addView(tv)
    }

    private fun animate(view: View) {
        val anim = ObjectAnimator.ofFloat(view, "rotation", 360f)
        anim.duration = 600
        anim.start()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }

    }

}
