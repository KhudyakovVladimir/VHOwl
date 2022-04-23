package com.khudyakovvladimir.vhowl.game

import android.graphics.*
import android.view.SurfaceHolder

class DrawThread(
    var surfaceHolder: SurfaceHolder,
    var surfaceDrawView: SurfaceDrawView
    ): Thread() {

    private val targetFPS = 50
    var isDrawThreadRunning: Boolean = false

    companion object {
        var canvas: Canvas? = null
        //var isRunning: Boolean = false
    }

    fun setRunning(value: Boolean) {
        isDrawThreadRunning = value
    }

    override fun run() {

        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetTime = (1000 / targetFPS).toLong()

        while (isDrawThreadRunning) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    //this.surfaceDrawView.check()
                    //this.surfaceDrawView.update()
                    surfaceDrawView.draw(canvas!!)
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

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            if (waitTime < 100) {
                waitTime = 100
            }

            try {
                sleep(waitTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}