package com.mag.denis.game.objects

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.mag.denis.game.R
import java.util.*

class FloorSet(resources: Resources) {

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private val grassBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_grass)
    private val seaBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_sea)
    private val floorGameObjects = mutableListOf<FloorGameObject>()

    init {
        var lineOffset = true
        val rand = Random()
        for (y in 0..screenHeight step grassBitmap.height / 2) {
            lineOffset = !lineOffset
            for (x in 0..screenWidth step grassBitmap.width) {
                val value = rand.nextInt(10)
                if (value == 0) {
                    continue
                }
                val bitmap = if (value ==1) {
                    seaBitmap
                } else {
                    grassBitmap
                }
                val xPosition = if (lineOffset) x + grassBitmap.width / 2 else x
                floorGameObjects.add(FloorGameObject(xPosition.toFloat(), y.toFloat(), bitmap))
            }
        }

    }

    fun draw(canvas: Canvas, paint: Paint) {
        for (floor in floorGameObjects) {
            canvas.drawBitmap(floor.image, floor.x, floor.y, paint)
        }
    }

    fun update() {

    }

}
