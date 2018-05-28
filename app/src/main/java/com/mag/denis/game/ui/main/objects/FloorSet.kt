package com.mag.denis.game.ui.main.objects

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.mag.denis.game.R


class FloorSet(context: Context, resources: Resources) {

    private val leafDrawable = ContextCompat.getDrawable(context, R.drawable.ic_leaf)!!
    private val leafBitmap = drawableToBitmap(leafDrawable)
    private val floorGameObjects = mutableListOf<FloorGameObject>()

    private val level1 = listOf(
            listOf("1", "1", "1", "1", "1"),
            listOf("0", "0", "0", "1", "0"),
            listOf("0", "0", "0", "1", "0"),
            listOf("0", "1", "1", "1", "0"))

    init {
        var screenHeight = 300f //TODO get screen width
        var screenWidth = 300f

        val height = level1.size
        val width = level1.first().size

        var xPosition = screenWidth / 2 - width / 2
        var yPosition = screenHeight / 2 - height / 2

        for (row in level1) {
            var xTmp = xPosition
            for (tile in row) {
                if (tile == "1") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafBitmap))

                }
                xTmp += leafBitmap.width
            }
            yPosition += leafBitmap.height
        }
    }

    fun draw(canvas: Canvas, paint: Paint) {
        for (floor in floorGameObjects) {
            canvas.drawBitmap(floor.image, floor.x, floor.y, paint)
        }
    }

    fun update() {

    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    fun getTileWidth(): Int {
        return leafBitmap.width
    }

    fun getTileHeight(): Int {
        return leafBitmap.height
    }

    fun getInitPosition(): Pair<Float, Float>? {
        val first = floorGameObjects.firstOrNull()
        return if (first != null) {
            Pair(first.x, first.y)
        } else {
            null
        }
    }

    fun isPositionOnFloorSet(numAction: Int, x: Float, y: Float): Boolean {
        return if (numAction < floorGameObjects.size) {

            val floorTile = floorGameObjects[numAction + 1]
            val imageWidthBounds = floorTile.image.width / 2
            val imageHeightBounds = floorTile.image.height / 2
            floorGameObjects.firstOrNull {
                x > (it.x - imageWidthBounds) && x < (it.x + imageWidthBounds)
                        && y > (it.y - imageHeightBounds) && y < (it.y + imageHeightBounds)
            } != null
        } else {
            false
        }
    }
}
