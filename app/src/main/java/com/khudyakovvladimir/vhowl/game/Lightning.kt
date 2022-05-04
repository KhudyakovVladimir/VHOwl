package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.khudyakovvladimir.vhowl.R

class Lightning (
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float,
    context: Context
) {

    private val lightningBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.lightning)

    fun drawLightning(canvas: Canvas?) {
        canvas?.drawBitmap(lightningBitmap!!, x, y, null)
    }
}