package com.mag.denis.game

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.mag.denis.game.objects.ImageGameObject

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val gameThread: GameThread
    private var imageGameObject: ImageGameObject? = null
    private var paint: Paint

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)

        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        //Nothing to do here
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // Game objects
        imageGameObject = ImageGameObject(BitmapFactory.decodeResource(resources, R.drawable.ball))

        // Start the game thread
        gameThread.setRunning(true)
        gameThread.start()
    }

    fun update() {
        imageGameObject!!.update()
    }


    fun render(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
        imageGameObject!!.draw(canvas, paint)
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread.setRunning(false)
                gameThread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
    }
}
