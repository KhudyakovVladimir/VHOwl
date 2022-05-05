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
    var isDanger: Boolean,
    context: Context
) {

    private val cloudBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cloud)
    private val cloudDangerBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cloud_danger)

    fun drawCloud(canvas: Canvas?) {
        if (isDanger) {
            canvas?.drawBitmap(cloudDangerBitmap, x - 100F, y - 100F, null)
        }else {
            canvas?.drawBitmap(cloudBitmap, x - 100F, y - 100F, null)
        }
    }
}