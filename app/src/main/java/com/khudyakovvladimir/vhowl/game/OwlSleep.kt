package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.khudyakovvladimir.vhowl.R

class OwlSleep @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
): View(context, attributeSet, defStyleAttr) {

    val paint = Paint()
    val owlSleepBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sleeping_owl)
    private var onClickListener: OnClickListener? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.alpha = 255
        paint.color = Color.WHITE
        paint.strokeWidth = 3f

        canvas?.drawBitmap(owlSleepBitmap, (canvas.width / 2).toFloat() - (owlSleepBitmap.width / 2), 100F, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val drawCanvas = Canvas(canvasBitmap!!)
    }

    private fun init(set: AttributeSet) {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("TAG", "OwlSleep - onTouchEvent() - ACTION.DOWN")
                isPressed = true
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> return false
        }
        invalidate()
        return true
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP &&
            (event.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || event.keyCode == KeyEvent.KEYCODE_ENTER)
        ) {
            if (onClickListener != null) onClickListener!!.onClick(this)
        }
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        isPressed = if (event.action == MotionEvent.ACTION_DOWN) {
            true
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (onClickListener != null) onClickListener!!.onClick(this)
            false
        } else {
            false
        }
        return super.dispatchTouchEvent(event)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }
}