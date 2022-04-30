package com.khudyakovvladimir.vhowl.utils

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import com.khudyakovvladimir.vhowl.R
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

    fun fadeInView(img: View, duration: Long) {
        val fadeIn: Animation = AlphaAnimation(0F, 1F)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = duration
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeIn)
        img.visibility = View.VISIBLE
    }

    fun getGameSessionTime(startTime: Int, endTime: Int, context: Context){
        var word = ""
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        val date1 = Date(startTime.toLong())
        val date = Date(endTime.toLong())
        val diff = date.time - date1.time
        word = simpleDateFormat.format(diff)
        context.data.time = word
    }

//    fun alpha(context: Context, view: View) {
//        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.alpha)
//        view.startAnimation(animation)
//    }
//
//    fun rotate(context: Context, view: View) {
//        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.rotate)
//        view.startAnimation(animation)
//    }
//
//    fun scale(context: Context, view: View) {
//        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_two)
//        view.startAnimation(animation)
//    }
//
//    fun translate(context: Context, view: View) {
//        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.transform)
//        view.startAnimation(animation)
//    }
//
//    fun rightToLeft(context: Context, view: View) {
//        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.right_to_left)
//        view.startAnimation(animation)
//    }
//
//    fun leftToRight(context: Context, view: View) {
//        val animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.left_to_right)
//        view.startAnimation(animation)
//    }
}