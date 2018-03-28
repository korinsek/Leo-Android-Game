package com.mag.denis.game

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) : Thread() {

    private var canvas: Canvas? = null
    private var running: Boolean = false
    private val targetFPS = 500

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetTime = (1000 / targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = this.surfaceHolder.lockCanvas() // locking the canvas allows us to draw on to it
                synchronized(surfaceHolder) {
                    if (canvas != null) {
                        this.gameView.update()
                        this.gameView.render(canvas!!)
                    }
                }
            } catch (e: Exception) {
                //TODO handle exception
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        //TODO handle exception
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            try {
                if (waitTime > 0) {
                    sleep(waitTime)
                }
            } catch (e: Exception) {
                //TODO handle exception
            }
        }
    }

    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }
}
