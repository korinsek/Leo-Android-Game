package com.mag.denis.game.objects

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class ImageGameObject(var image: Bitmap) {

    private var x: Float
    private var y: Float
    private var xVelocity = 5
    private var yVelocity = 5
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        x = screenWidth / 2f
        y = screenHeight / 2f
    }

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(image, x, y, paint)
    }

    fun update() {
        if (x > screenWidth - image.width || x < image.width/2) {
            xVelocity *= -1
        }
        if (y > screenHeight - image.height || y < image.height/2) {
            yVelocity *= -1
        }

        x += xVelocity
        y += yVelocity
    }

}
