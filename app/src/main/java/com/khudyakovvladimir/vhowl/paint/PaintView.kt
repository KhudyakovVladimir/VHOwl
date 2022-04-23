package com.khudyakovvladimir.vhowl.paint

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.util.toRange
import com.khudyakovvladimir.vhowl.R
import kotlin.random.Random


class PaintView(context: Context): SurfaceView(context), SurfaceHolder.Callback {
    var paintThread: PaintThread? = null
    var canvasArray: Array<Pixel> = arrayOf()
    val canvasWidth = 1080
    val canvasHeight = 2040

    var path: Path? = null
    var paint: Paint? = null
    var canvas: Canvas? = null
    private var bitmapOwl: Bitmap? = null
    private var bitmapMouse: Bitmap? = null
    private var bitmapBackground: Bitmap? = null

    //val pixel = Pixel(500F, 1000F)
    val owl = Owl(500F, 1000F)
    val mouse = Mouse(980F, 1800F, 15F,100F,true)

    init {
        holder.addCallback(this)
        paintThread = PaintThread(holder, this)

        path = Path()
        paint = Paint()
        canvas = Canvas()

        customizePaint(Color.YELLOW)

        bitmapOwl = BitmapFactory.decodeResource(this.resources, R.drawable.flying_owl)
        bitmapMouse = BitmapFactory.decodeResource(this.resources, R.drawable.mouse)
        bitmapBackground = BitmapFactory.decodeResource(this.resources, R.drawable.forest_background_small)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        paintThread?.isRunning = true
        paintThread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    fun initCanvasArray() {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        //customizePaint(Color.YELLOW)
        //drawPixel(pixel, canvas)
        //drawItem(pixel, canvas, bitmapOwl!!)

        drawBackgroundDrawable(canvas)
        drawOwl(canvas)
        drawMouse(canvas)
    }

    fun customizePaint(color: Int) {
        paint?.alpha = 255
        paint?.color = color
        paint?.strokeWidth = 3f
    }

    fun drawPixel(pixel: Pixel, canvas: Canvas?) {
        canvas?.drawRect(pixel.x, pixel.y, pixel.x + 10F, pixel.y + 10F, paint!!)
    }

    fun drawMouse(canvas: Canvas?) {
        canvas?.drawBitmap(bitmapMouse!!, mouse.x, mouse.y, null)
    }

    fun drawOwl(canvas: Canvas?) {
        canvas?.drawBitmap(bitmapOwl!!, owl.x - 100F, owl.y - 100F, null)
    }

    fun drawItem(pixel: Pixel, canvas: Canvas?, bitmap: Bitmap) {
        canvas?.drawBitmap(bitmap, pixel.x - 100F, pixel.y - 100F, null)
        //to move the object with you finger
        //*param -100F(finger diameter)
        //canvas?.drawBitmap(bitmap, pixel.x - 100F, pixel.y - 100F, paint)
    }

    fun drawBackgroundDrawable(canvas: Canvas?) {
        canvas?.drawBitmap(bitmapBackground!!, 0F, 0F, null)
    }

    fun update() {
        updateOwlLocation()
        updateMouseLocation()
        //isCaughtByOwl(mouse, owl)
    }

    fun updateOwlLocation() {
        val delta = 5F
        if (owl.x < 950) {
            owl.x =  owl.x + delta
            //pixel.y =  pixel.y + delta
        }
        if (owl.x == 950F) {
            owl.x = 0F
            //owl.y = 0F
        }
    }

    fun updateMouseLocation() {
        val delta = mouse.speed
        if (mouse.x > -40F) {
            mouse.x =  mouse.x - delta
            //mouse.y =  mouse.y + delta
        }
        if (mouse.x == -40F) {
            mouse.x = 1080F
            //mouse.y = 1000F
        }
    }

    //stay here
    fun isCaughtByOwl(mouse: Mouse, owl: Owl): Boolean {
        Log.d("TAG", "isCaughtByOwl()")
       // if(mouse.x == owl.x && mouse.y + 10 == owl.y + 10) {
        if(owl.x in mouse.x - mouse.radius..mouse.x + mouse.radius && owl.y in mouse.y - mouse.radius..mouse.y + mouse.radius) {
            Log.d("TAG", "___GAME---OVER___")
            mouse.isAlive = false
            mouse.x = 980F
            //mouse.y = 1800F
            val randomPositionByY = (1200..2000).random().toFloat()
            val randomSpeed = (10..25).random().toFloat()
            mouse.y = randomPositionByY
            mouse.speed = randomSpeed
            return true
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //Log.d("TAG", "ACTION_DOWN")
                owl.x = touchX
                owl.y = touchY
                isCaughtByOwl(mouse, owl)
                //drawPixel(pixel, canvas)
                //drawItem(pixel, canvas, bitmapOwl!!)
                Log.d("TAG", "mouse.x = ${mouse.x}")
                Log.d("TAG", "mouse.y = ${mouse.y}")
                Log.d("TAG", "owl.x = ${owl.x}")
                Log.d("TAG", "owl.y = ${owl.y}")
            }
            MotionEvent.ACTION_MOVE -> {
                //Log.d("TAG", "ACTION_MOVE")
                owl.x = touchX
                owl.y = touchY
                isCaughtByOwl(mouse, owl)
//                Log.d("TAG", "mouse.x = ${mouse.x}")
//                Log.d("TAG", "mouse.y = ${mouse.y}")
//                Log.d("TAG", "owl.x = ${owl.x}")
//                Log.d("TAG", "owl.y = ${owl.y}")
                //drawPixel(pixel, canvas)
                //drawItem(pixel, canvas, bitmapOwl!!)
            }
            MotionEvent.ACTION_UP -> {
                //Log.d("TAG", "ACTION_UP")
                owl.x = touchX
                owl.y = touchY
                //drawPixel(pixel, canvas)
                //drawItem(pixel, canvas, bitmapOwl!!)
                drawOwl(canvas)
            }
            else -> return false
        }
        invalidate()
        return true
    }
}