package com.mag.denis.game.ui.main.objects

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.mag.denis.game.R


class GameActor(resources: Resources, private val moveWidth: Int, private val moveHeight: Int, var x: Float = 0f, var y: Float = 0f) {
    private val actorBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_actor)
    private var onMoveListener: ActorListener? = null

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(actorBitmap, x, y, paint)
    }

    fun moveUp() {
        val animator = ValueAnimator.ofFloat(y, y - moveHeight)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            y = (animation.animatedValue as Float)
            onMoveListener?.onMove()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onMoveListener?.onAnimationEnd()
            }
        })
        animator.start()

    }

    fun moveDown() {
        val animator = ValueAnimator.ofFloat(y, y + moveHeight)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            y = (animation.animatedValue as Float)
            onMoveListener?.onMove()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onMoveListener?.onAnimationEnd()
            }
        })
        animator.start()
    }

    fun moveRight() {
        val animator = ValueAnimator.ofFloat(x, x + moveWidth)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            x = (animation.animatedValue as Float)
            onMoveListener?.onMove()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onMoveListener?.onAnimationEnd()
            }
        })
        animator.start()
    }

    fun moveLeft() {
        val animator = ValueAnimator.ofFloat(x, x - moveWidth)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            x = (animation.animatedValue as Float)
            onMoveListener?.onMove()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onMoveListener?.onAnimationEnd()
            }
        })
        animator.start()
    }

    fun setOnMoveListener(listener: ActorListener) {
        onMoveListener = listener
    }


    interface ActorListener {
        fun onMove()
        fun onAnimationEnd()
    }

}
