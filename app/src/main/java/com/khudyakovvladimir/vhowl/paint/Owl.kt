package com.khudyakovvladimir.vhowl.paint

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Owl(
    var x: Float,
    var y: Float
) {
    fun update() {
        var delta = 5F
        if (this.x < 950) {
            this?.x =  this?.x!! + delta
            this?.y =  this?.y!! + delta
            //Log.d("TAG", "x = $x")
        }
        if (this.x == 950F) {
            //Log.d("TAG", "---Next---")
            this.x = 0F
            this.y = 0F
        }
    }

    fun draw(canvas: Canvas, bitmap: Bitmap) {
        val paint = Paint()
        paint.alpha = 255
        paint.color = Color.YELLOW
        paint.strokeWidth = 3f

        //canvas.drawRect(x, y, x + width, y+height, paint)
        //canvas.drawRect(x, y, x + 100, y + 100, paint)
        canvas.drawBitmap(bitmap, x + 100, y + 100, paint)
    }

    fun drawPixel(pixel: Pixel, canvas: Canvas?) {
        //canvas?.drawRect(pixel.x, pixel.y, pixel.x + 1, pixel.y + 1, paint!!)
    }

    fun drawItem(x: Float, y: Float, canvas: Canvas?, bitmap: Bitmap) {
        //canvas?.drawBitmap(bitmap, x, y, paint)
    }
}