package com.khudyakovvladimir.vhowl.paint

import android.graphics.Canvas
import android.view.SurfaceHolder


class PaintThread(
    var surfaceHolder: SurfaceHolder,
    var paintView: PaintView,
    ): Thread() {

    val monitor = 1
    var optionInput = 0
    private var itemX = 0f
    val canvasWidth = 1080
    val canvasHeight = 2400

    companion object {
        private var canvas: Canvas? = null
    }

    var isRunning = false

    override fun run() {
        while (isRunning) {
            canvas = null

            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    paintView.update()
                    paintView.draw(canvas!!)
                    paintView.updateMouseLocation()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }


//    fun onTouch(e: MotionEvent): Boolean {
//        if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_MOVE) {
//            synchronized(monitor) { actionOnTouch(e.rawX, e.rawY) }
//        }
//        return false
//    }
//
//    fun actionOnTouch(x: Float, y: Float) {
//        if(optionInput>=0)
//            itemX=x;
//        else {
//            while (itemX >= 0 && itemX <= canvasWidth) {
//                if (x < canvasWidth / 2 && itemX >= canvasWidth/35)
//                    itemX -= canvasWidth / 35
//                else if (x > canvasWidth / 2 && itemX <= canvasWidth-canvasWidth/35)
//                    itemX += canvasWidth / 35
//                break
//            }
//        }
//    }
}