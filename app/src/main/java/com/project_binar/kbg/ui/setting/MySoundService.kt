package com.project_binar.kbg.ui.setting

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.project_binar.kbg.R
import com.project_binar.kbg.util.SuitPrefs


class MySoundService : Service() {
    private lateinit var suitPrefs: SuitPrefs

    var backsound: MediaPlayer? = null
    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        suitPrefs = SuitPrefs(this)
        backsound = MediaPlayer.create(this, R.raw.backsound)
        backsound!!.isLooping = true // Set looping
        backsound!!.setVolume(50f, 50f)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        backsound!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        backsound!!.stop()
        backsound!!.release()
    }
}