package com.mag.denis.game.ui.main.actions.intro

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_DOWN
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_RIGHT
import com.mag.denis.game.ui.main.actions.actionpseudoview.AbsPseudoView
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.objects.FloorSet
import com.mag.denis.game.ui.main.view.action.ActionImageView
import com.mag.denis.game.ui.main.view.action.ConditionView
import com.mag.denis.game.ui.main.view.action.LoopView
import kotlinx.android.synthetic.main.partial_action_animation.view.*

class IntroView : ConstraintLayout {
    //TODO code for animations can be peatier without duplicated code, its something to be done later.

    private var callback: AnimationIntroCallback? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        inflate(context, R.layout.partial_action_animation, this)
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    }

    fun startAnimationAction() {
        show()
        val actionRightAnim = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT, viewWidth = resources.getDimensionPixelSize(R.dimen.actionWidth))
        val handPointer = ImageView(context)
        handPointer.setImageResource(R.drawable.ic_touch)
        animationConstraint.addView(actionRightAnim)
        animationConstraint.addView(handPointer)

        val anim = TranslateAnimation(0f, 300f, 50f, 50f)
        anim.duration = 2000

        anim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.width / 2, 50f, height.toFloat() - handPointer.height * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        val arrowJump = AppCompatImageView(context)
                        arrowJump.setImageResource(R.drawable.ic_curved_arrow)
                        arrowJump.x = animationConstraint.width / 2.5f
                        arrowJump.y = animationConstraint.height / 7f

                        animationConstraint.addView(arrowJump)

                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActions())
                    }
                })

                anim1.fillAfter = true
                handPointer.startAnimation(anim1)
            }
        })

        anim.fillAfter = true
        handPointer.startAnimation(anim)
        actionRightAnim.startAnimation(anim)

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }

    fun startAnimationLoop() {
        show()
        val actionRightAnim = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT, viewWidth = resources.getDimensionPixelSize(R.dimen.actionWidth))
        val handPointer = ImageView(context)
        val loop1 = LoopView(context)

        actionRightAnim.visibility = View.GONE
        handPointer.setImageResource(R.drawable.ic_touch)
        animationConstraint.addView(actionRightAnim)
        animationConstraint.addView(handPointer)
        animationConstraint.addView(loop1)

        val anim = TranslateAnimation(0f, 300f, 300f, 50f)
        anim.duration = 2000

        anim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                loop1.setValue(4)
                actionRightAnim.visibility = View.VISIBLE
                actionRightAnim.bringToFront()
                handPointer.bringToFront()
                val anim = TranslateAnimation(0f, 300f, 0f, 120f)
                anim.duration = 2000

                anim.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        animationConstraint.removeView(actionRightAnim)
                        loop1.getPlaceholder().addView(actionRightAnim)
                        actionRightAnim.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                        val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.width / 2, 50f, height.toFloat() - handPointer.height * 1.5f)
                        anim1.duration = 2000

                        anim1.setAnimationListener(object : Animation.AnimationListener {

                            override fun onAnimationStart(animation: Animation) {}

                            override fun onAnimationRepeat(animation: Animation) {}

                            override fun onAnimationEnd(animation: Animation) {
                                callback?.animationWantResetGame()
                                callback?.onStartAnimationClick(getActionsLoop())
                            }
                        })

                        anim1.fillAfter = true
                        handPointer.startAnimation(anim1)
                    }
                })

                anim.fillAfter = true
                handPointer.startAnimation(anim)
                actionRightAnim.startAnimation(anim)
            }
        })

        anim.fillAfter = true
        handPointer.startAnimation(anim)
        loop1.startAnimation(anim)

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }

    fun startAnimationLoopAndIf(supportFragmentManager: FragmentManager) {
        show()
        val handPointer = ImageView(context)
        val loop1 = LoopView(context)
        val ifView = ConditionView(context, supportFragmentManager)
        ifView.onColorTypeSelect(FloorSet.TYPE_LEAF_GREEN, R.drawable.ic_leaf_green)
        val actionRightAnim = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT, viewWidth = resources.getDimensionPixelSize(R.dimen.actionWidth))
        val actionDownAnim = ActionImageView(context, R.drawable.ic_arrow_down, MainActivity.ACTION_DOWN, viewWidth = resources.getDimensionPixelSize(R.dimen.actionWidth))

        actionRightAnim.visibility = View.GONE
        actionDownAnim.visibility = View.GONE
        ifView.visibility = View.GONE
        handPointer.setImageResource(R.drawable.ic_touch)
        animationConstraint.addView(actionRightAnim)
        animationConstraint.addView(handPointer)
        animationConstraint.addView(loop1)
        animationConstraint.addView(ifView)
        animationConstraint.addView(actionDownAnim)

        val anim = TranslateAnimation(0f, 300f, 200f, 50f)
        anim.duration = 2000

        anim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                loop1.setValue(6)
                ifView.visibility = View.VISIBLE
                handPointer.bringToFront()
                val anim = TranslateAnimation(0f, 300f, 100f, 80f)
                anim.duration = 2000

                anim.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        animationConstraint.removeView(ifView)
                        loop1.getPlaceholder().addView(ifView)
                        actionRightAnim.visibility = View.VISIBLE
                        actionRightAnim.bringToFront()
                        handPointer.bringToFront()

                        ifView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                        val anim = TranslateAnimation(0f, 300f, 100f, 200f)
                        anim.duration = 2000

                        anim.setAnimationListener(object : Animation.AnimationListener {

                            override fun onAnimationStart(animation: Animation) {}

                            override fun onAnimationRepeat(animation: Animation) {}

                            override fun onAnimationEnd(animation: Animation) {
                                animationConstraint.removeView(actionRightAnim)

                                ifView.getTruePlaceholder().addView(actionRightAnim)
                                actionRightAnim.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                                ifView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                                actionDownAnim.visibility = View.VISIBLE
                                actionDownAnim.bringToFront()
                                handPointer.bringToFront()

                                val anim = TranslateAnimation(0f, 300f, 100f, 450f)
                                anim.duration = 2000

                                anim.setAnimationListener(object : Animation.AnimationListener {

                                    override fun onAnimationStart(animation: Animation) {}

                                    override fun onAnimationRepeat(animation: Animation) {}

                                    override fun onAnimationEnd(animation: Animation) {
                                        animationConstraint.removeView(actionDownAnim)

                                        ifView.getFalsePlaceholder().addView(actionDownAnim)
                                        actionDownAnim.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                                        val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.width / 2, 50f, height.toFloat() - handPointer.height * 1.5f)
                                        anim1.duration = 2000

                                        anim1.setAnimationListener(object : Animation.AnimationListener {

                                            override fun onAnimationStart(animation: Animation) {}

                                            override fun onAnimationRepeat(animation: Animation) {}

                                            override fun onAnimationEnd(animation: Animation) {
                                                callback?.animationWantResetGame()
                                                callback?.onStartAnimationClick(getActionsIfLoop())
                                            }
                                        })

                                        anim1.fillAfter = true
                                        handPointer.startAnimation(anim1)
                                    }
                                })

                                anim.fillAfter = true
                                handPointer.startAnimation(anim)
                                actionDownAnim.startAnimation(anim)
                            }
                        })

                        anim.fillAfter = true
                        handPointer.startAnimation(anim)
                        actionRightAnim.startAnimation(anim)
                    }
                })

                anim.fillAfter = true
                handPointer.startAnimation(anim)
                ifView.startAnimation(anim)
            }
        })

        anim.fillAfter = true
        handPointer.startAnimation(anim)
        loop1.startAnimation(anim)

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }


    fun startAnimationFlowAction() {
        show()
        val actionRightAnim = ActionImageView(context, R.drawable.ic_arrow_right, MainActivity.ACTION_RIGHT, viewWidth = resources.getDimensionPixelSize(R.dimen.actionWidthSmall))
        val handPointer = ImageView(context)
        handPointer.setImageResource(R.drawable.ic_touch)
        animationConstraint.addView(actionRightAnim)
        animationConstraint.addView(handPointer)

        val anim = TranslateAnimation(0f, 340f, 50f, 150f)
        anim.duration = 2000

        anim.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.width / 2, 50f, height.toFloat() - handPointer.height * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        val arrowJump = AppCompatImageView(context)
                        arrowJump.setImageResource(R.drawable.ic_curved_arrow)
                        arrowJump.x = animationConstraint.width / 2.5f
                        arrowJump.y = animationConstraint.height / 7f

                        animationConstraint.addView(arrowJump)

                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActions())
                    }
                })

                anim1.fillAfter = true
                handPointer.startAnimation(anim1)
            }
        })

        anim.fillAfter = true
        handPointer.startAnimation(anim)
        actionRightAnim.startAnimation(anim)

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }

    fun startAnimationPseudo1Action(reservedWordsPattern: String) {
        show()
        delayedTextView.animateText("${AbsPseudoView.MOVE_RIGHT}\n${AbsPseudoView.MOVE_RIGHT}", reservedWordsPattern, object : DelayedTextView.TextTypedCallback {
            override fun onTextTypingEnd() {
                val handPointer = ImageView(context)
                handPointer.setImageResource(R.drawable.ic_touch)
                animationConstraint.addView(handPointer)

                val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.drawable.intrinsicWidth / 2, 50f, height.toFloat() - handPointer.drawable.intrinsicHeight * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        val arrowJump1 = AppCompatImageView(context)
                        arrowJump1.setImageResource(R.drawable.ic_curved_arrow)
                        arrowJump1.x = animationConstraint.width / 2.5f
                        arrowJump1.y = animationConstraint.height / 7f

                        val arrowJump2 = AppCompatImageView(context)
                        arrowJump2.setImageResource(R.drawable.ic_curved_arrow)
                        arrowJump2.x = animationConstraint.width / 2.5f + arrowJump1.drawable.intrinsicWidth
                        arrowJump2.y = animationConstraint.height / 7f

                        animationConstraint.addView(arrowJump1)
                        animationConstraint.addView(arrowJump2)

                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActionsPseudo1())
                    }
                })

                anim1.fillAfter = true
                handPointer.startAnimation(anim1)
            }
        })

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }


    fun startAnimationPseudo2Action(reservedWordsPattern: String) {
        show()
        delayedTextView.animateText("${AbsPseudoView.MOVE_RIGHT}\n${AbsPseudoView.MOVE_RIGHT}\n${AbsPseudoView.MOVE_DOWN}", reservedWordsPattern, object : DelayedTextView.TextTypedCallback {
            override fun onTextTypingEnd() {
                val handPointer = ImageView(context)
                handPointer.setImageResource(R.drawable.ic_touch)
                animationConstraint.addView(handPointer)

                val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.drawable.intrinsicWidth / 2, 50f, height.toFloat() - handPointer.drawable.intrinsicHeight * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        val arrowJump1 = AppCompatImageView(context)
                        arrowJump1.setImageResource(R.drawable.ic_curved_arrow)
                        arrowJump1.x = animationConstraint.width / 2.5f
                        arrowJump1.y = animationConstraint.height / 7f

                        val arrowJump2 = AppCompatImageView(context)
                        arrowJump2.setImageResource(R.drawable.ic_curved_arrow)
                        arrowJump2.x = animationConstraint.width / 2.5f + arrowJump1.drawable.intrinsicWidth
                        arrowJump2.y = animationConstraint.height / 7f

                        animationConstraint.addView(arrowJump1)
                        animationConstraint.addView(arrowJump2)

                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActionsPseudo2())
                    }
                })

                anim1.fillAfter = true
                handPointer.startAnimation(anim1)
            }
        })

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }

    fun startAnimationPseudo3Action(reservedWordsPattern: String, code: String) {
        show()
        delayedTextView.animateText(code, reservedWordsPattern, object : DelayedTextView.TextTypedCallback {
            override fun onTextTypingEnd() {
                val handPointer = ImageView(context)
                handPointer.setImageResource(R.drawable.ic_touch)
                animationConstraint.addView(handPointer)

                val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.drawable.intrinsicWidth / 2, 50f, height.toFloat() - handPointer.drawable.intrinsicHeight * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActionsPseudo3())
                    }
                })

                anim1.fillAfter = true
                handPointer.startAnimation(anim1)
            }
        })

        btSkipIntro.visibility = View.VISIBLE
        btSkipIntro.setOnClickListener {
            hide()
            callback?.animationWantResetGame()
        }
    }

    fun startAnimationPseudo4Action(reservedWordsPattern: String, code: String) {
        show()
        delayedTextView.animateText(code, reservedWordsPattern, object : DelayedTextView.TextTypedCallback {
            override fun onTextTypingEnd() {
                val handPointer = ImageView(context)
                handPointer.setImageResource(R.drawable.ic_touch)
                animationConstraint.addView(handPointer)

                val anim1 = TranslateAnimation(300f, width.toFloat() / 3 - handPointer.drawable.intrinsicWidth / 2, 50f, height.toFloat() - handPointer.drawable.intrinsicHeight * 1.5f)
                anim1.duration = 2000

                anim1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        callback?.animationWantResetGame()
                        callback?.onStartAnimationClick(getActionsPseudo4())
                    }
                })

                anim1.fillAfter = true
                handPointer.startAnimation(anim1)
            }
        })

        btSkipIntro.visibility = View.VISIBLE
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

    private fun getActionsLoop(): ArrayList<Command> {
        return arrayListOf(Loop(4, listOf(Action(ACTION_RIGHT))))
    }

    private fun getActionsIfLoop(): ArrayList<Command> {
        return arrayListOf(Loop(6, listOf(IfCondition(ColorCondition(FloorSet.TYPE_LEAF_GREEN, Condition.TYPE_TRUE), listOf(Action(ACTION_RIGHT)), listOf(Action(ACTION_DOWN))))))
    }

    private fun getActionsPseudo1(): ArrayList<Command> {
        return arrayListOf(Action(ACTION_RIGHT), Action(ACTION_RIGHT))
    }

    private fun getActionsPseudo2(): ArrayList<Command> {
        return arrayListOf(Action(ACTION_RIGHT), Action(ACTION_RIGHT), Action(ACTION_DOWN))
    }

    private fun getActionsPseudo3(): ArrayList<Command> {
        return arrayListOf(Loop(4, listOf(Action(ACTION_RIGHT))))
    }

    private fun getActionsPseudo4(): ArrayList<Command> {
        return arrayListOf(Loop(6, listOf(IfCondition(ColorCondition(FloorSet.TYPE_LEAF_GREEN, Condition.TYPE_TRUE), listOf(Action(ACTION_RIGHT)), listOf(Action(ACTION_DOWN))))))
    }

    fun hide() {
        this.visibility = View.GONE
    }

    fun show() {
        this.visibility = View.VISIBLE
    }
}
