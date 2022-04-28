package com.khudyakovvladimir.vhowl.game

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(
    var surfaceHolder: SurfaceHolder,
    var gameView: GameView,
    ): Thread() {

    val monitor = 1
    var optionInput = 0
    private var itemX = 0f
    val canvasWidth = 1080
    val canvasHeight = 2400
    val targetFPS = 50

    companion object {
        private var canvas: Canvas? = null
    }

    var isRunning = false

    override fun run() {

        var lastLoopTime = System.nanoTime()
        val TARGET_FPS = 20
        val OPTIMAL_TIME = (1000000000 / TARGET_FPS).toLong()
        var lastFpsTime: Long = 0

        while (isRunning) {
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

                    gameView.update()
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