package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.*
import com.khudyakovvladimir.vhowl.R

class Owl(
    var x: Float,
    var y: Float,
    context: Context
) {

    val owlBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.flying_owl)

    fun drawOwl(canvas: Canvas?) {
        canvas?.drawBitmap(owlBitmap, x - 100F, y - 100F, null)
    }
}