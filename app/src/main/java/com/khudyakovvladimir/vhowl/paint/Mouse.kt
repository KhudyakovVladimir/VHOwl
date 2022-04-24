package com.khudyakovvladimir.vhowl.paint

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

    val mouseBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mouse)

    fun drawMouse(canvas: Canvas?) {
        canvas?.drawBitmap(mouseBitmap!!, x, y, null)
    }

    fun move() {
//        while (x > 100F) {
//            x -= 10F
//        }
        val delta = 5F
        if (x < 950) {
            x += delta
            //pixel.y =  pixel.y + delta
        }
        if (x == 950F) {
            x = 0F
            y = 0F
        }
    }
}