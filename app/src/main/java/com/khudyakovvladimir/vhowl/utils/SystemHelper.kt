package com.khudyakovvladimir.vhowl.utils

import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import androidx.fragment.app.Fragment
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
}