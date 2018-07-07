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


class FloorSet(context: Context, resources: Resources, level1: List<List<String>>) {

    private val leafGreenBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_leaf_green)!!)
    private val leafBrownBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_leaf_brown)!!)
    private val floorGameObjects = mutableListOf<FloorGameObject>()

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
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafGreenBitmap, TYPE_LEAF_GREEN))//TODO TYPE
                }
                if (tile == "2") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafBrownBitmap, TYPE_LEAF_BROWN))//TODO TYPE
                }
                xTmp += leafGreenBitmap.width
            }
            yPosition += leafGreenBitmap.height
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
        return leafGreenBitmap.width
    }

    fun getTileHeight(): Int {
        return leafGreenBitmap.height
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

    fun getFloorplanObjectByPosition(x: Float, y: Float): FloorGameObject? {
        return floorGameObjects.find { checkXCoordinate(it, x, y) }
    }

    private fun checkXCoordinate(item: FloorGameObject, x: Float, y: Float): Boolean {
        val xCheck = (x <= item.x + leafGreenBitmap.width / 2 && x >= item.x - leafGreenBitmap.width / 2)
        val yCheck = (y <= item.y + leafGreenBitmap.height / 2 && y >= item.y - leafGreenBitmap.height / 2)
        return xCheck && yCheck
    }

    companion object {
        const val TYPE_LEAF_GREEN = 1
        const val TYPE_LEAF_BROWN = 2
    }
}
