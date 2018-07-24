package com.mag.denis.game.ui.main.actions.actionblockview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_RIGHT
import com.mag.denis.game.ui.main.model.Action
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.action.ActionImageView
import kotlinx.android.synthetic.main.partial_action_animation.view.*

class AnimationIntroView : ConstraintLayout {
    private var callback: AnimationIntroCallback? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_action_animation, this)
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val paddingPx = resources.getDimensionPixelSize(R.dimen.actionPadding)
        this.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
    }

    fun startAnimation() {
        val actionRightAnim = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT, viewWidth = resources.getDimensionPixelSize(R.dimen.actionWidth))
        val arrow = ImageView(context)
        arrow.setImageResource(R.drawable.ic_touch)
        animationConstraint.addView(actionRightAnim)
        animationConstraint.addView(arrow)

        val anim = TranslateAnimation(0f, 220f, 0f, 0f)
        anim.duration = 2000

        anim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                val anim1 = TranslateAnimation(220f, width.toFloat() / 3 - arrow.width, 0f, height.toFloat() - arrow.height * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActions())
                    }
                })

                anim1.fillAfter = true
                arrow.startAnimation(anim1)
            }
        })

        anim.fillAfter = true
        arrow.startAnimation(anim)
        actionRightAnim.startAnimation(anim)

        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }

    fun setAnimationIntroCallback(callback: AnimationIntroCallback) {
        this.callback = callback
    }

    interface AnimationIntroCallback {
        fun onStartAnimationClick(commands: ArrayList<Command>)
        fun animationWantResetGame()
    }

    private fun getActions(): ArrayList<Command> {
        return arrayListOf(Action(ACTION_RIGHT))
    }

    fun hide(){
        this.visibility = View.GONE
    }
}
