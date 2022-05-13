package com.khudyakovvladimir.vhowl.utils

import com.khudyakovvladimir.vhowl.R
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.content.Context
import android.graphics.Color.alpha
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import androidx.fragment.app.Fragment
import com.google.android.material.animation.AnimationUtils
import com.khudyakovvladimir.vhowl.app.data
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class SystemHelper @Inject constructor() {

    fun getCellSize(fragment: Fragment): Float {
        val w: WindowManager = fragment.activity!!.windowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)

        var widthPixels = metrics.widthPixels
        var heightPixels = metrics.heightPixels
        try {
            widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
            heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
        } catch (ignored: Exception) {
        }
        try {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
            widthPixels = realSize.x
            heightPixels = realSize.y
        } catch (ignored: Exception) {
        }

        Log.d("TAG", "screenHeight = $heightPixels")
        Log.d("TAG", "screenWidth = $widthPixels")

        return (widthPixels / 30).toFloat()
    }

    fun getScreenResolution(fragment: Fragment): List<Int> {
        val list = emptyList<Int>().toMutableList()
        val w: WindowManager = fragment.activity!!.windowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)

        var widthPixels = metrics.widthPixels
        var heightPixels = metrics.heightPixels
        try {
            widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
            heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
        } catch (ignored: Exception) {
        }
        try {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
            widthPixels = realSize.x
            heightPixels = realSize.y
        } catch (ignored: Exception) {
        }

        Log.d("TAG", "screenHeight = $heightPixels")
        Log.d("TAG", "screenWidth = $widthPixels")

        list[0] = widthPixels
        list[1] = heightPixels

        return list
    }

    fun fadeInView(view: View, duration: Long) {
        val fadeIn: Animation = AlphaAnimation(0F, 1F)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = duration
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        view.startAnimation(fadeIn)
        view.visibility = View.VISIBLE
    }

    fun pulse(view: View, context: Context) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.pulse)
        view.startAnimation(animation)
    }

    fun alpha(context: Context, view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.pulse)
        view.startAnimation(animation)
    }

    fun getGameSessionTime(startTime: Int, endTime: Int, context: Context){
        var word = ""
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        val date1 = Date(startTime.toLong())
        val date = Date(endTime.toLong())
        val diff = date.time - date1.time
        Log.d("TAG", "diff = $diff")
        word = simpleDateFormat.format(diff)
        context.data.time = word
    }

    fun checkTimeToThunder(startTime: Int, _delay: Long): Boolean{
        var start = Date(startTime.toLong())
        var delay = Date(_delay)
        var summaryTime = start.time + delay.time
        var currentTime = System.currentTimeMillis()
        //Log.d("TAG", "start = $start")
        //Log.d("TAG", "delay = $delay")
        //Log.d("TAG", "summaryTime = $summaryTime")
        //Log.d("TAG", "currentTime = $currentTime")
        if(currentTime == summaryTime) {
            Log.d("TAG", "THUNDER !!!")
            return true
        }
        return false
    }


}