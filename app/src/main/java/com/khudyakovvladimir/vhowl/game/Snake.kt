package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.khudyakovvladimir.vhowl.R

class Snake(
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float,
    var isAlive: Boolean,
    context: Context
) {
    private val snakeBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.snake)

    fun drawSnake(canvas: Canvas?) {
        canvas?.drawBitmap(snakeBitmap!!, x, y, null)
    }
}