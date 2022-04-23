package com.khudyakovvladimir.vhowl.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import javax.inject.Inject


class CanvasView @Inject constructor(context: Context): View(context) {

    var path: Path? = null
    var paint: Paint? = null
    var canvas: Canvas? = null
    private var bitmap: Bitmap? = null

    var paintColor = -0x1000000

    init {
        path = Path()
        paint = Paint()
        canvas = Canvas()

        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeJoin = Paint.Join.ROUND
        paint!!.strokeCap = Paint.Cap.ROUND
        paint!!.strokeWidth = 20F

        paint?.color = paintColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap!!, 0f, 0f, paint)
        canvas?.drawPath(path!!, paint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> path!!.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> path!!.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                //Log.d("TAG", "x = $touchX")
                //Log.d("TAG", "y = $touchY")
                //Log.d("TAG", "canvas?.width = ${canvas?.width}")
                //Log.d("TAG", "canvas?.height = ${canvas?.height}")
                //Log.d("TAG", "canvas?.height = ${calculateCellSize()}")
                calculateCellSize()
                //getScreenSizeResolution()
                canvas!!.drawPath(path!!, paint!!)
                path!!.reset()
                paint!!.xfermode = null
            }
            else -> return false
        }

        invalidate()
        return true
    }

    private fun calculateCellSize(): Float {
        val cellSizeInPixels = (canvas?.width!! / 30).toFloat()
        paint?.strokeWidth = cellSizeInPixels
        Log.d("TAG", "cellSizeInPixels = $cellSizeInPixels")
        return cellSizeInPixels
    }


}