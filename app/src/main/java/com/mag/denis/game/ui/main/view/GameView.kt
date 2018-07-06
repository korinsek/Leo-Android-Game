package com.mag.denis.game.ui.main.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_DOWN
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_LEFT
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_RIGHT
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_UP
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.objects.FloorSet
import com.mag.denis.game.ui.main.objects.GameActor
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private var gameThreadSubscription: Disposable? = null
    private var mainCanvas: Canvas? = null
    private var paint: Paint

    private var floorGameObjects: FloorSet? = null
    private var actor: GameActor? = null
    private lateinit var actions: List<Command>
    private var execActionPosition = 0

    private var messageCallback: OnMessageCallback? = null

    private var initPositionX = 0f
    private var initPositiony = 0f

    init {
        holder.addCallback(this)

        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        //Nothing to do here
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        initGameObjects()
        // Start the game thread
        initGameThread()
    }

    private fun initGameObjects() {
        floorGameObjects = FloorSet(context, resources)
        initPositionX = floorGameObjects?.getInitPosition()?.first ?: 0f
        initPositiony = floorGameObjects?.getInitPosition()?.second ?: 0f
        actor = GameActor(resources, floorGameObjects!!, floorGameObjects!!.getTileWidth(), floorGameObjects!!.getTileHeight(), initPositionX, initPositiony)
        actor?.setOnMoveListener(object : GameActor.ActorListener {
            override fun onMove() {
                invalidate()
            }

            override fun onAnimationEnd() {
                if (!floorGameObjects!!.isPositionOnFloorSet(execActionPosition, actor!!.x, actor!!.y)) {
                    actor?.stopAnimation()
                    messageCallback?.showGameMessage(R.string.main_message_fall_into_see)
                }
            }
        })
    }

    fun update() {
        floorGameObjects?.update()
    }


    fun render(canvas: Canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.backgroundBlueLight))
        floorGameObjects?.draw(canvas, paint)
        actor?.draw(canvas, paint)
    }

    fun doActions(actions: List<Command>, conditions: Conditions? = null) {
        this.actions = actions
        if (actions.isNotEmpty()) {
            for (action in actions) {
                when (action) {
                    is Loop -> {
                        if (action.repeat > 0) {
                            action.repeat--
                            doActions(action.commands)
                            doActions(actions)
                        }
                    }
                    is IfCondition -> {
                        val conditionsTrue = if (conditions == null) {
                            action.condition
                            Conditions(arrayListOf())
                        } else {
                            conditions
                        }
                        val condition = when (action.condition) {
                            is ColorCondition -> ColorCondition(action.condition.leafColorType, Condition.TYPE_TRUE)
                            else -> throw IllegalStateException("Condition dont exists")
                        }
                        conditionsTrue.conditions.add(condition)

                        val conditionsFalse = if (conditions == null) {
                            action.condition
                            Conditions(arrayListOf())
                        } else {
                            conditions
                        }

                        val condition1 = when (action.condition) {
                            is ColorCondition -> ColorCondition(action.condition.leafColorType, Condition.TYPE_FALSE)
                            else -> throw IllegalStateException("Condition dont exists")
                        }
                        conditionsFalse.conditions.add(condition1)

                        doActions(action.trueCommands, conditionsTrue)
                        doActions(action.falseCommands, conditionsFalse)
                    }
                    is Action -> {
                        when (action.type) {
                            ACTION_UP -> actor?.moveUp(conditions)
                            ACTION_DOWN -> actor?.moveDown(conditions)
                            ACTION_RIGHT -> actor?.moveRight(conditions)
                            ACTION_LEFT -> actor?.moveLeft(conditions)
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder?) {
        gameThreadSubscription?.dispose()
    }

    private fun initGameThread() {
        gameThreadSubscription?.dispose()
        gameThreadSubscription = Observable.interval(1000L / targetFPS, TimeUnit.MILLISECONDS)
                .flatMapSingle {
                    Single.fromCallable {
                        val canvas = this.holder.lockCanvas() // locking the canvas allows us to draw on to it
                        synchronized(holder) {
                            if (canvas != null) {
                                mainCanvas = canvas
                                this.update()
                                this.render(canvas)
                            }
                        }
                    }.map { holder.unlockCanvasAndPost(mainCanvas) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing to do
                }, {
                    //TODO error
                })
    }

    fun resetGame() {
        initGameObjects()
        initGameThread()
    }

    fun setOnMessageCallback(callback: OnMessageCallback) {
        messageCallback = callback
    }

    interface OnMessageCallback {
        fun showGameMessage(@StringRes messageId: Int)
    }

    companion object {
        private const val targetFPS = 25L
    }
}
