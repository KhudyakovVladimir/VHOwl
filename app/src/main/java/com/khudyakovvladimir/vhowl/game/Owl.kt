package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.*
import com.khudyakovvladimir.vhowl.R

class Owl(
    var x: Float,
    var y: Float,
    context: Context
) {

    private val owlBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.flying_owl)
    private val mouseBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mouse)

    fun drawOwl(canvas: Canvas?) {
        canvas?.drawBitmap(owlBitmap, x - 100F, y - 100F, null)
        canvas?.drawBitmap(mouseBitmap, x - 100F, y - 100F, null)
    }
}