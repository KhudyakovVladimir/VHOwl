package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import javax.inject.Inject
import kotlin.random.Random

class SurfaceDrawView @Inject constructor(context: Context, attributeSet: AttributeSet): SurfaceView(context), SurfaceHolder.Callback {
    var drawThread: DrawThread? = null
    var arrayOfCells: Array<Array<Cell>>? = null
    var path: Path? = null
    var paint: Paint? = null
    var canvas: Canvas? = null
    private var bitmap: Bitmap? = null

    private var cell: Cell? = null
    var delta: Float = 1F

    var paintColor = -0x1000000

    init {
        holder.addCallback(this)
        drawThread = DrawThread(holder, this)
        path = Path()
        paint = Paint()
        canvas = Canvas()
    //bitmap = BitmapFactory.decodeResource(this.resources, com.khudyakovvladimir.vhgameoflife.R.drawable.flying_owl)
    //cell = Cell(100F,100F,cellSize,true)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        initArrayOfCells()
        drawThread?.setRunning(true)
        //Game.isPlaying = true
        if(Game.isPlaying) {
            drawThread?.start()
        }

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                drawThread?.setRunning(false)
                drawThread?.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            // нажатие
            MotionEvent.ACTION_DOWN -> {
                Log.d("TAG", "ACTION_DOWN")
                Log.d("TAG", "arrayOfCells?.size - ${arrayOfCells?.get(0)}")

            }
            // движение
            MotionEvent.ACTION_MOVE -> {
                Log.d("TAG", "ACTION_MOVE")
            }
            // отпускание
            MotionEvent.ACTION_UP -> {
                Log.d("TAG", "ACTION_UP")
                //canvas!!.drawPath(path!!, paint!!)
                //path!!.reset()
                //paint!!.xfermode = null
                //Log.d("TAG", "x = ${event.x}")
                //Log.d("TAG", "y = ${event.y}")
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun check() {
        cell?.check()
    }

    fun update() {
        if(arrayOfCells != null) {
            arrayOfCells!!.forEach { r -> r.forEach { c -> c.update(arrayOfCells!!) } }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        arrayOfCells!!.forEach { r -> r.forEach { c -> c.draw(canvas) } }
    }

    fun initArrayOfCells() {
        if(arrayOfCells == null) {
            val rnd = Random.Default
            val rowCellCount = 30
            val rowCount = 30
            val cellPerPixels = ((this.width - 50) / 30).toFloat()

            Log.d("TAG", "rowCellCount = $rowCellCount")
            Log.d("TAG", "rowCount = $rowCount")
            Log.d("TAG", "cellPerPixels = $cellPerPixels")

            arrayOfCells = Array(rowCount) {r -> Array(rowCellCount) { i ->
                Cell(
                    i * cellPerPixels + cellPerPixels,
                    r * cellPerPixels + cellPerPixels,
                    r,
                    i,
                    35f,
                    35f,
                    (rnd.nextInt() % 2) == 0
                )
            } }
        }
    }

    fun f() {

    }
}