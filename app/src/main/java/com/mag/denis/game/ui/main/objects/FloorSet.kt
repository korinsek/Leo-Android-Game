package com.mag.denis.game.ui.main.objects

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import com.mag.denis.game.R
import com.mag.denis.game.utils.ImageUtils.drawableToBitmap


class FloorSet(context: Context, resources: Resources, level1: List<List<String>>) {

    private val leafGreenBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_leaf_green)!!)
    private val leafBrownBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_leaf_brown)!!)
    private val leafFinishBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_leaf_dark_yelow)!!)
    private val starBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_star_pick)!!)
    private val floorGameObjects = mutableListOf<FloorGameObject>()

    init {
        var screenHeight = 300f //TODO get screen width
        var screenWidth = 300f

        val height = level1.size
        val width = level1.first().size

        val margin = 10
        var xPosition = screenWidth / 2 - width / 2 + margin
        var yPosition = screenHeight / 2 - height / 2

        for (row in level1) {
            var xTmp = xPosition
            for (tile in row) {
                if (tile == "1") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafGreenBitmap, TYPE_LEAF_GREEN))
                }
                if (tile == "2") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafBrownBitmap, TYPE_LEAF_BROWN))
                }
                if (tile == "1S") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafGreenBitmap, TYPE_LEAF_GREEN))
                    floorGameObjects.add(FloorGameObject(xTmp + starBitmap.width, yPosition + starBitmap.height / 2, starBitmap, TYPE_LEAF_STAR))
                }
                if (tile == "2S") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafBrownBitmap, TYPE_LEAF_BROWN))
                    floorGameObjects.add(FloorGameObject(xTmp + starBitmap.width, yPosition + starBitmap.height / 2, starBitmap, TYPE_LEAF_STAR))
                }
                if (tile == "F") {
                    floorGameObjects.add(FloorGameObject(xTmp, yPosition, leafFinishBitmap, TYPE_LEAF_FINISH))//TODO finish leaf
                }
                xTmp += leafGreenBitmap.width
            }
            yPosition += leafGreenBitmap.height
        }
    }

    fun draw(canvas: Canvas, paint: Paint) {
        for (floor in floorGameObjects) {
            if (floor.visible) {
                canvas.drawBitmap(floor.image, floor.x, floor.y, paint)
            }
        }
    }

    fun update() {

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
            getFloorObjects(numAction, x, y).firstOrNull() != null
        } else {
            false
        }
    }

    private fun getFloorObjects(numAction: Int, x: Float, y: Float): List<FloorGameObject> {
        val floorTile = floorGameObjects[numAction + 1]
        val imageWidthBounds = floorTile.image.width / 2
        val imageHeightBounds = floorTile.image.height / 2
        return floorGameObjects.filter {
            x > (it.x - imageWidthBounds) && x < (it.x + imageWidthBounds)
                    && y > (it.y - imageHeightBounds) && y < (it.y + imageHeightBounds)
        }
    }

    fun checkForStar(numAction: Int, x: Float, y: Float): Boolean {
        return if (numAction < floorGameObjects.size) {
            val starObject = getFloorObjects(numAction, x, y).firstOrNull { it.leafColorType == TYPE_LEAF_STAR && it.visible }
            starObject?.visible = false
            starObject != null
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
        const val TYPE_LEAF_FINISH = 3
        const val TYPE_LEAF_STAR = 4
    }
}
