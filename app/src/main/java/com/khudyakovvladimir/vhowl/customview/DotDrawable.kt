package com.khudyakovvladimir.vhowl.customview

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class DotDrawable(private val position: Position): Drawable() {
    private val paint: Paint = Paint().apply { setARGB(255, 255, 150, 50) }

    override fun draw(canvas: Canvas) {
        val width = canvas.width / 15f
        val x: Float = position.x * width
        val y: Float = position.y * width
        canvas.drawRect(x, y, x + width, y + width, paint)
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}