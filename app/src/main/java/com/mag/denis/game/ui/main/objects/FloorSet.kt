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

    init {
        var initx = 300f
        var inity = 300f
        floorGameObjects.add(FloorGameObject(initx, inity, leafBitmap))
        initx += leafBitmap.width
        floorGameObjects.add(FloorGameObject(initx, inity, leafBitmap))
        inity += leafBitmap.height
        floorGameObjects.add(FloorGameObject(initx, inity, leafBitmap))
        inity += leafBitmap.height
        floorGameObjects.add(FloorGameObject(initx, inity, leafBitmap))
        initx += leafBitmap.width
        floorGameObjects.add(FloorGameObject(initx, inity, leafBitmap))

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
        return if(first!=null) {
            Pair(first.x, first.y)
        }else{
            null
        }
    }
}
