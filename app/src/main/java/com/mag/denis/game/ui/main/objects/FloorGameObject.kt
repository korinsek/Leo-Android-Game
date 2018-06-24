package com.mag.denis.game.ui.main.objects

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class FloorGameObject(val x: Float, val y: Float, val image: Bitmap, val leafColorType: Int) {

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(image, x, y, paint)
    }

    fun update() {

    }

}
