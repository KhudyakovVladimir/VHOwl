package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.khudyakovvladimir.vhowl.R

class Background(
    var x: Float,
    context: Context
) {
    val bitmapBackground = BitmapFactory.decodeResource(context.resources, R.drawable.backgroundwork)

    fun drawBackground(canvas: Canvas?) {
        canvas?.drawBitmap(bitmapBackground!!, x, 0F, null)
    }
}