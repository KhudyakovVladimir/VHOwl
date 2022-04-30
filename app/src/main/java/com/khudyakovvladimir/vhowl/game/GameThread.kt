package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import com.khudyakovvladimir.vhowl.app.data

class GameThread(
    var surfaceHolder: SurfaceHolder,
    var gameView: GameView,
    var context: Context
    ): Thread() {

    companion object {
        private var canvas: Canvas? = null
    }

    override fun run() {

        var lastLoopTime = System.nanoTime()
        val TARGET_FPS = 20
        val OPTIMAL_TIME = (1000000000 / TARGET_FPS).toLong()
        var lastFpsTime: Long = 0

        while (context.data.isRunning) {
            gameView.soundHelper.playSoundWind(false)
            canvas = null

            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {

                    val now = System.nanoTime()
                    val updateLength = now - lastLoopTime
                    lastLoopTime = now
                    val delta = updateLength / OPTIMAL_TIME.toDouble()

                    lastFpsTime += updateLength
                    if (lastFpsTime >= 1000000000) {
                        lastFpsTime = 0
                    }

                    if (!context.data.isPause) {
                        gameView.update()
                        gameView.soundHelper.playSoundWind(false)
                    }else {
                        gameView.soundOff()
                    }

                    gameView.draw(canvas!!)

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

            try {
                val gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000
                sleep(gameTime)
            } catch (e: java.lang.Exception) {
            }

        }
    }
}