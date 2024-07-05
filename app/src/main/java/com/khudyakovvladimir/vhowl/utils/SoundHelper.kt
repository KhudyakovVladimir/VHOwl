package com.khudyakovvladimir.vhowl.utils

import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import com.khudyakovvladimir.vhowl.R
import javax.inject.Inject

class SoundHelper @Inject constructor(val application: Application) {

    private var soundPool: SoundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
    private val snake = soundPool.load(application.applicationContext, R.raw.snake, 1)
    private val mouse = soundPool.load(application.applicationContext, R.raw.mouse_squeak,1)
    private val mouseWin = soundPool.load(application.applicationContext, R.raw.mouse_win,1)
    private val owl = soundPool.load(application.applicationContext, R.raw.owl,1)
    private val thunder = soundPool.load(application.applicationContext, R.raw.thunder,1)

    var mediaPlayer: MediaPlayer? = null

    init {
        mediaPlayer = MediaPlayer.create(application.applicationContext, R.raw.wind)
    }

    fun playSoundSnake(mute: Boolean) {
        if(!mute) {
            soundPool.play(snake, 0.05F, 0.05F, 0, 0, 1F)
        }
    }

    fun playSoundMouse(mute: Boolean) {
        if (!mute) {
            soundPool.play(mouse, 0.05F, 0.05F,0,0, 1F)
        }
    }

    fun playSoundMouseWin(mute: Boolean) {
        if (!mute) {
            soundPool.play(mouseWin, 0.05F, 0.05F,0,0, 1F)
        }
    }

    fun playSoundOwl(mute: Boolean) {
        if (!mute) {
            soundPool.play(owl, 0.3F, 0.3F,0,0, 1F)
        }
    }

    fun playSoundThunder(mute: Boolean) {
        if (!mute) {
            soundPool.play(thunder, 0.2F, 0.2F,0,0, 1F)
        }
    }

    fun playSoundWind(mute: Boolean) {
        if (!mute) {
            mediaPlayer!!.setVolume(10F, 10F)
            mediaPlayer!!.start()
        }
    }

    fun stopSoundWind() {
        mediaPlayer!!.stop()
        mediaPlayer = MediaPlayer.create(application.applicationContext, R.raw.wind)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun vibrate(context: Context, milliseconds: Long) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.EFFECT_TICK))
    }
}