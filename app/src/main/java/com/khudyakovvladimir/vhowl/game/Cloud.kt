package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.khudyakovvladimir.vhowl.R

class Cloud (
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float,
    var delay: Float,
    context: Context
) {

    val cloudBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cloud)

    fun drawCloud(canvas: Canvas?) {
        canvas?.drawBitmap(cloudBitmap, x, y, null)
    }
}