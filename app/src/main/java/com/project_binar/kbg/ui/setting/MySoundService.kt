package com.project_binar.kbg.ui.setting

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.project_binar.kbg.R
import com.project_binar.kbg.util.SuitPrefs


class MySoundService : Service() {
    private lateinit var suitPrefs: SuitPrefs
    private var backsound: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        suitPrefs = SuitPrefs(this)
//        Toast.makeText(this,"Music Service Created",Toast.LENGTH_SHORT).show()
        backsound = MediaPlayer.create(this, R.raw.backsound)
        backsound!!.isLooping = true // Set looping
        backsound!!.setVolume(1F, 1F)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        backsound!!.start()
//        Toast.makeText(this,"Music Service Started",Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    override fun onDestroy() {
        backsound!!.stop()
//        Toast.makeText(this,"Music Service Stopped",Toast.LENGTH_SHORT).show()
        backsound!!.release()
    }
}