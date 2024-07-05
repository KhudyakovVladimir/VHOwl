package com.khudyakovvladimir.vhowl.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.navigation.findNavController
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.app.data
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import javax.inject.Inject

class GameView(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    var gameThread: GameThread? = null
    var path: Path? = null
    var paint: Paint? = null
    var canvas: Canvas? = null

    @Inject
    lateinit var soundHelper: SoundHelper

    @Inject
    lateinit var systemHelper: SystemHelper

    var countOfSnake = 0
    var countOfMouse = 0
    var startTime = 0

    val owl = Owl(100F, 1000F, context)
    val snake = Snake(1000F, 1600F, 70F, 100F, true, context)
    val mouse = Mouse(1000F, 1800F, 30F,100F,true, context)
    val cloud = Cloud(1000F, 400F, 20F, 30F, 800F, true ,context)
    val cloud2 = Cloud(1300F, 800F, 15F, 30F, 1000F, false ,context)
    val cloud3 = Cloud(1600F, 1200F, 5F, 30F, 1000F, false, context)
    val lightning = Lightning(2800F, 800F, 20F, 30F, context)
    val background = Background(0F, context)

    init {

        context.appComponent.injectGameView(this)

        holder.addCallback(this)
        gameThread = GameThread(holder, this, context)

        path = Path()
        paint = Paint()
        canvas = Canvas()

        customizePaint(Color.YELLOW)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        context.data.isRunning = true
        gameThread?.start()
        startTime = System.currentTimeMillis().toInt()
        Log.d("TAG", "startTime = $startTime")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        context.data.isRunning = false
        while (retry) {
            try {
                gameThread!!.join()
                retry = false
            } catch (e: InterruptedException) {

            }
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawBackground(canvas)
        drawOwl(canvas)
        drawMouse(canvas)
        drawSnake(canvas)
        drawCloud(canvas)
        drawLightning(canvas)
    }

    fun customizePaint(color: Int) {
        paint?.alpha = 255
        paint?.color = color
        paint?.strokeWidth = 3f
    }

    fun drawSnake(canvas: Canvas?) {
        snake.drawSnake(canvas)
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

    fun drawLightning(canvas: Canvas?) {
        lightning.drawLightning(canvas)
    }

    fun drawBackground(canvas: Canvas?) {
        background.drawBackground(canvas)
    }

    fun update() {
        updateBackgroundLocation(background)
        updateOwlLocation()
        updateSnakeLocation(snake)
        updateMouseLocation(mouse)
        updateCloudLocation(cloud)
        updateCloudLocation(cloud2)
        updateCloudLocation(cloud3)
        updateLightningLocation(lightning)
    }

    fun updateBackgroundLocation(background: Background) {
        val delta = 5F

        if (background.x > -2500) {
            background.x = background.x - delta
        }
        if (background.x < -2499) {
            background.x = 0F
        }
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

    fun updateSnakeLocation(snake: Snake) {
        val delta = snake.speed
        if (snake.x > -360F) {
            snake.x = snake.x - delta
        }
        if (snake.x < -300F) {
            snake.x = 980F
            val randomPositionByY = (1100..1900).random().toFloat()
            val randomSpeed = (40..50).random().toFloat()
            snake.y = randomPositionByY
            snake.speed = randomSpeed
            if(owl.x in snake.x - snake.radius..snake.x + snake.radius && owl.y in snake.y - snake.radius..snake.y + snake.radius) {
                countOfSnake++
                soundHelper.playSoundSnake(false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    soundHelper.vibrate(context, 60)
                }
                snake.isAlive = false
                snake.x = 980F
                val randomPositionByY = (1100..1900).random().toFloat()
                val randomSpeed = (40..50).random().toFloat()
                snake.y = randomPositionByY
                snake.speed = randomSpeed
                context.data.countOfSnake = countOfSnake
            }
        }
    }

    fun updateMouseLocation(mouse: Mouse) {
        val delta = mouse.speed
        if (mouse.x > -360F) {
            mouse.x = mouse.x - delta
        }
        if (mouse.x < -300F) {
            soundHelper.playSoundMouseWin(false)
            soundHelper.stopSoundWind()
            context.data.isRunning = false
            findNavController().navigate(R.id.startFragment)
        }
    }

    fun updateCloudLocation(_cloud: Cloud) {
        val delta = _cloud.speed
        if (_cloud.x > -240F) {

            if (_cloud.isDanger) {
                if (_cloud.x == _cloud.delay) {
                    lightning.x = _cloud.x - 100
                    lightning.y = _cloud.y
                    val random = ((3..9).random().toFloat()) * 100
                    cloud.delay = random
                    cloud.isDanger = false
                }
            }

            _cloud.x = _cloud.x - delta
        }
        if (_cloud.x < -200F) {
            _cloud.x = 1080F
            _cloud.y = (600..1800).random().toFloat()

            when((0..1).random()) {
                0 -> { _cloud.isDanger = false }
                1 -> { _cloud.isDanger = true }
            }

        }
    }

    fun updateLightningLocation(lightning: Lightning) {
        val delta = lightning.speed
        if (lightning.y < 2400F) {
            lightning.x = lightning.x - delta
            lightning.y = lightning.y + delta
        }
    }

    fun detectCollisions() {
        isCaughtBySnake(snake, owl)
        isCaughtByMouse(mouse, owl)
        isCaughtByCloud(cloud, owl)
        isCaughtByCloud(cloud2, owl)
        isCaughtByCloud(cloud3, owl)
        isCaughtByLightning(lightning, owl)
    }

    fun isCaughtBySnake(_snake:Snake, owl: Owl): Boolean {
        if(owl.x in _snake.x - _snake.radius.._snake.x + _snake.radius && owl.y in _snake.y - _snake.radius.._snake.y + _snake.radius) {
            countOfSnake++
            soundHelper.playSoundSnake(false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                soundHelper.vibrate(context, 20)
            }
            snake.isAlive = false
            snake.x = 980F
            val randomPositionByY = (1100..1900).random().toFloat()
            val randomSpeed = (75..85).random().toFloat()
            snake.y = randomPositionByY
            snake.speed = randomSpeed
            context.data.countOfSnake = countOfSnake

            //owl.x = (200..950).random().toFloat()
            //owl.y = (1400..1800).random().toFloat()

            return true
        }
        return false
    }

    fun isCaughtByMouse(_mouse: Mouse, owl: Owl): Boolean {
        if(owl.x in _mouse.x - _mouse.radius.._mouse.x + _mouse.radius && owl.y in _mouse.y - _mouse.radius.._mouse.y + _mouse.radius) {
            countOfMouse++
            soundHelper.playSoundMouse(false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                soundHelper.vibrate(context, 20)
            }
            mouse.isAlive = false
            mouse.x = 980F
            val randomPositionByY = (1200..2000).random().toFloat()
            val randomSpeed = (33..40).random().toFloat()
            mouse.y = randomPositionByY
            mouse.speed = randomSpeed
            context.data.countOfMouse = countOfMouse
            return true
        }
        return false
    }

    fun isCaughtByCloud(_cloud: Cloud, owl: Owl): Boolean {
        if(owl.x in _cloud.x - _cloud.radius.._cloud.x + _cloud.radius && owl.y in _cloud.y - _cloud.radius.._cloud.y + _cloud.radius) {
            soundHelper.stopSoundWind()
            soundHelper.playSoundThunder(false)
            context.data.isRunning = false
            findNavController().navigate(R.id.startFragment)
            return true
        }
        return false
    }

    fun isCaughtByLightning(_lightning: Lightning, owl: Owl): Boolean {
        if(owl.x in _lightning.x + 100 - _lightning.radius.._lightning.x + 100 + _lightning.radius && owl.y in _lightning.y + 100 - _lightning.radius.._lightning.y + 100 + _lightning.radius) {
            soundHelper.stopSoundWind()
            soundHelper.playSoundThunder(false)
            context.data.isRunning = false
            findNavController().navigate(R.id.startFragment)
            return true
        }
        return false
    }

    fun soundOff() {
        soundHelper.stopSoundWind()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                soundHelper.playSoundOwl(false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    soundHelper.vibrate(context, 10)
                }
                owl.x = touchX
                owl.y = touchY
            }
            MotionEvent.ACTION_MOVE -> {
                owl.x = touchX
                owl.y = touchY
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