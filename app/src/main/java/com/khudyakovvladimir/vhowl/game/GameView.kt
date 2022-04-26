package com.khudyakovvladimir.vhowl.game

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.navigation.findNavController
import com.khudyakovvladimir.vhowl.R
import java.util.concurrent.TimeUnit


class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback {
    var gameThread: GameThread? = null
    var path: Path? = null
    var paint: Paint? = null
    var canvas: Canvas? = null

    private var bitmapBackground: Bitmap? = null

    val owl = Owl(500F, 1000F, context)
    val mouse = Mouse(980F, 1800F, 5F,100F,true, context)
    val cloud = Cloud(980F, 400F, 20F, 100F, 1000F, context)
    val cloud2 = Cloud(1280F, 600F, 15F, 100F, 1000F, context)
    val cloud3 = Cloud(1080F, 600F, 5F, 100F, 1000F, context)
    val listOfClouds = generateClouds()

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)

        path = Path()
        paint = Paint()
        canvas = Canvas()

        customizePaint(Color.YELLOW)

        bitmapBackground = BitmapFactory.decodeResource(this.resources, R.drawable.forest_background_small)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        gameThread?.isRunning = true
        gameThread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawBackgroundDrawable(canvas)
        drawOwl(canvas)
        drawMouse(canvas)
        drawCloud(canvas)
    }

    fun customizePaint(color: Int) {
        paint?.alpha = 255
        paint?.color = color
        paint?.strokeWidth = 3f
    }

    fun drawMouse(canvas: Canvas?) {
        mouse.drawMouse(canvas)
    }

    fun drawOwl(canvas: Canvas?) {
        owl.drawOwl(canvas)
    }

    fun drawCloud(canvas: Canvas?) {
        cloud.drawCloud(canvas)
        cloud2.drawCloud(canvas)
        cloud3.drawCloud(canvas)
    }

    fun drawBackgroundDrawable(canvas: Canvas?) {
        canvas?.drawBitmap(bitmapBackground!!, 0F, 0F, null)
    }

    fun update() {
        updateOwlLocation()
        updateMouseLocation()
        updateCloudLocation(cloud)
        updateCloudLocation(cloud2)
        updateCloudLocation(cloud3)
    }

    fun updateOwlLocation() {
        val delta = 5F
        if (owl.x < 950) {
            owl.x =  owl.x + delta
        }
        if (owl.x == 950F) {
            owl.x = 0F
        }
    }

    fun updateMouseLocation() {
        val delta = mouse.speed
        if (mouse.x > -20F) {
            mouse.x = mouse.x - delta
        }
        if (mouse.x == 0F) {
            mouse.x = 1080F
        }
    }

    fun updateCloudLocation(cloud: Cloud) {
        Log.d("TAG", "updateCloudLocation()")
        val delta = cloud.speed
        if (cloud.x > -20F) {
            cloud.x = cloud.x - delta
        }
        if (cloud.x < 0F) {
            cloud.x = 1080F
            cloud.y = (600..1800).random().toFloat()
        }
    }

    fun updateCloudsLocation(list: List<Cloud>) {
        for(i in 0..list.size - 1) {
            val delta = list[i].speed
            if (list[i].x > 0F) {
                list[i].x =  list[i].x - delta
            }
            if (list[i].x == 20F) {
                list[i].x = 1080F
            }
        }
    }

    fun generateClouds(): List<Cloud> {
        val list = arrayListOf(cloud, cloud, cloud)

        for (i in 0 until list.size) {

            val randomPositionByY = (300..1000).random().toFloat()
            val randomSpeed = (5..10).random().toFloat()
            val randomDelay = (1..3).random().toFloat()

            list[i].x = 980F
            list[i].y = randomPositionByY
            list[i].speed = randomSpeed
            list[i].radius = 100f
            list[i].delay = randomDelay

            //= Cloud(1800F, randomPositionByY, randomSpeed, 100F, randomDelay, context)
        }
        return list
    }

    fun isCaughtByOwl(_mouse: Mouse, owl: Owl): Boolean {
        Log.d("TAG", "isCaughtByOwl()")
        if(owl.x in _mouse.x - _mouse.radius.._mouse.x + _mouse.radius && owl.y in _mouse.y - _mouse.radius.._mouse.y + _mouse.radius) {
            mouse.isAlive = false
            mouse.x = 980F
            val randomPositionByY = (1200..2000).random().toFloat()
            val randomSpeed = (20..45).random().toFloat()
            mouse.y = randomPositionByY
            mouse.speed = randomSpeed
            //Log.d("TAG", "mouse.speed = ${mouse.speed}")
            return true
        }
        return false
    }

    fun isCaughtByCloud(_cloud: Cloud, owl: Owl): Boolean {
        Log.d("TAG", "isCaughtByOwl()")
        if(owl.x in _cloud.x - _cloud.radius.._cloud.x + _cloud.radius && owl.y in _cloud.y - _cloud.radius.._cloud.y + _cloud.radius) {
            _cloud.x = 1080F
            val randomPositionByY = (1200..2000).random().toFloat()
            val randomSpeed = (5..20).random().toFloat()
            _cloud.y = randomPositionByY
            _cloud.speed = randomSpeed
            gameThread?.isRunning = false
            findNavController().navigate(R.id.startFragment)
            return true
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                owl.x = touchX
                owl.y = touchY
                isCaughtByOwl(mouse, owl)
                isCaughtByCloud(cloud, owl)
                isCaughtByCloud(cloud2, owl)
                isCaughtByCloud(cloud3, owl)
            }
            MotionEvent.ACTION_MOVE -> {
                owl.x = touchX
                owl.y = touchY
                isCaughtByOwl(mouse, owl)
                isCaughtByCloud(cloud, owl)
                isCaughtByCloud(cloud2, owl)
                isCaughtByCloud(cloud3, owl)
            }
            MotionEvent.ACTION_UP -> {
                owl.x = touchX
                owl.y = touchY
                drawOwl(canvas)
            }
            else -> return false
        }
        invalidate()
        return true
    }
}