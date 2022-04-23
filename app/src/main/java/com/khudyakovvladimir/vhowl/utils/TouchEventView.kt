package com.khudyakovvladimir.vhowl.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class MyTouchEventView(context: Context?) : View(context) {
    private val paint: Paint = Paint()
    private val path: Path = Path()
    var btnChange: Button
    var params: ViewGroup.LayoutParams

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointX = event.x
        val pointY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(pointX, pointY)
                return true
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(pointX, pointY)
            else -> return false
        }
        postInvalidate()
        return true
    }

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 1f
        btnChange = Button(context)
        btnChange.text = "Change color"
        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        btnChange.layoutParams = params
        btnChange.setOnClickListener { paint.color = Color.GREEN }
    }
}