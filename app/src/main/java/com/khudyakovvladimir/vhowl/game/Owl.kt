package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.*
import com.khudyakovvladimir.vhowl.R
import java.util.concurrent.TimeUnit

class Owl(
    var x: Float,
    var y: Float,
    context: Context
) {

    private val owlBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.flying_owl)

    fun drawOwl(canvas: Canvas?) {
        canvas?.drawBitmap(owlBitmap, x - 100F, y - 100F, null)
    }
}