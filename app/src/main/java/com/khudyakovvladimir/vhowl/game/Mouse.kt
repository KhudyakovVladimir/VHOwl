package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.khudyakovvladimir.vhowl.R

class Mouse(
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float,
    var isAlive: Boolean,
    context: Context
) {

    private val mouseBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mouse)

    fun drawMouse(canvas: Canvas?) {
        canvas?.drawBitmap(mouseBitmap!!, x, y, null)
    }
}