package com.mag.denis.game.ui.main.objects

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.model.Conditions


class GameActor(resources: Resources, private val floorGameObjects: FloorSet, private val moveWidth: Int, private val moveHeight: Int, var x: Float = 0f, var y: Float = 0f) : Animator.AnimatorListener {
    private val actorBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_actor)
    private var onMoveListener: ActorListener? = null

    private val actionList: ArrayList<ActorAction> = ArrayList()
    private var isAnimating = false
    private var stoped = false

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(actorBitmap, x, y, paint)
    }

    fun moveUp(conditions: Conditions?) {
        executeAnimator(ActorAction(MOVE_UP, conditions))
    }

    fun moveDown(conditions: Conditions?) {
        executeAnimator(ActorAction(MOVE_DOWN, conditions))
    }

    fun moveRight(conditions: Conditions?) {
        executeAnimator(ActorAction(MOVE_RIGHT, conditions))
    }

    fun moveLeft(conditions: Conditions?) {
        executeAnimator(ActorAction(MOVE_LEFT, conditions))
    }

    fun setOnMoveListener(listener: ActorListener) {
        onMoveListener = listener
    }

    private fun executeAnimator(action: ActorAction) {
        actionList.add(action)
        if (!isAnimating && actionList.isNotEmpty()) {
            isAnimating = true
            executeNext()
        }
    }

    private fun executeNext() {
        val action = actionList.first()
        actionList.removeAt(0)

        val conditions = action.conditions
        val tile = floorGameObjects.getFloorplanObjectByPosition(x, y)

        //TODO CHECK conditions if we execute action



        val animator = when (action.action) {
            MOVE_RIGHT -> {
                val a = ValueAnimator.ofFloat(x, x + moveWidth)
                a.addUpdateListener { animation ->
                    x = (animation.animatedValue as Float)
                    onMoveListener?.onMove()
                }
                a
            }
            MOVE_LEFT -> {
                val a = ValueAnimator.ofFloat(x, x - moveWidth)
                a.addUpdateListener { animation ->
                    x = (animation.animatedValue as Float)
                    onMoveListener?.onMove()
                }
                a
            }
            MOVE_DOWN -> {
                val a = ValueAnimator.ofFloat(y, y + moveHeight)
                a.addUpdateListener { animation ->
                    y = (animation.animatedValue as Float)
                    onMoveListener?.onMove()
                }
                a
            }
            MOVE_UP -> {
                val a = ValueAnimator.ofFloat(y, y - moveHeight)
                a.addUpdateListener { animation ->
                    y = (animation.animatedValue as Float)
                    onMoveListener?.onMove()
                }
                a
            }
            else -> throw IllegalStateException("Unknown action")
        }
        animator.duration = 1000
        animator.addListener(this)
        animator.start()
    }

    interface ActorListener {
        fun onMove()
        fun onAnimationEnd()
    }

    override fun onAnimationEnd(animation: Animator) {
        onMoveListener?.onAnimationEnd()
        if (!stoped && actionList.isNotEmpty()) {
            executeNext()
        } else {
            isAnimating = false
        }
    }

    override fun onAnimationRepeat(animation: Animator?) {
        //Nothing to do
    }

    override fun onAnimationCancel(animation: Animator?) {
        //Nothing to do
    }

    override fun onAnimationStart(animation: Animator?) {
        //Nothing to do
    }

    fun stopAnimation() {
        stoped = true
    }

    companion object {
        private const val MOVE_RIGHT = "move_right"
        private const val MOVE_LEFT = "move_left"
        private const val MOVE_UP = "move_up"
        private const val MOVE_DOWN = "move_down"
    }

    data class ActorAction(val action: String, val conditions: Conditions?)
}
