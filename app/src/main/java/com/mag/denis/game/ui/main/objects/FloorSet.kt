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

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private val grassDrawable = ContextCompat.getDrawable(context, R.drawable.bg_tile_yelow)!!
    private val seaDrawable = ContextCompat.getDrawable(context, R.drawable.bg_tile_brown)!!
    //    private val routeDrawable = ContextCompat.getDrawable(context, R.drawable.bg_tile_yelow)!!
    private val grassBitmap = drawableToBitmap(grassDrawable)
    private val yellowBitmap = drawableToBitmap(seaDrawable)
    //    private val routeBitmap = drawableToBitmap(routeDrawable)
    private val floorGameObjects = mutableListOf<FloorGameObject>()

    init {
        var lineOffset = true
//        val rand = Random()
//        for (y in 0..screenHeight step grassBitmap.height / 2) {
//            lineOffset = !lineOffset
//            for (x in 0..screenWidth step grassBitmap.width) {
//                val value = rand.nextInt(10)
//                if (value == 0) {
//                    continue
//                }
//                val bitmap = if (value ==1) {
//                    seaBitmap
//                } else {
//                    grassBitmap
//                }
//                val xPosition = if (lineOffset) x + grassBitmap.width / 2 else x
//                floorGameObjects.add(FloorGameObject(xPosition.toFloat(), y.toFloat(), bitmap))
//            }
//        }

        val floorTileMargin = 2
        for (y in grassBitmap.height..screenHeight - grassBitmap.height * 2 step grassBitmap.height + floorTileMargin) {
            for (x in grassBitmap.width..screenWidth - grassBitmap.width step grassBitmap.width + floorTileMargin) {

                val bitmap = if (y !in 200..500) {
                    yellowBitmap
//                    seaBitmap
                } else {
                    grassBitmap
                }
                floorGameObjects.add(FloorGameObject(x.toFloat(), y.toFloat(), bitmap))
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
        return grassBitmap.width
    }

    fun getTileHeight(): Int {
        return grassBitmap.height
    }
}
