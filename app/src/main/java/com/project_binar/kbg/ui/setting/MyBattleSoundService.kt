package com.project_binar.kbg.ui.setting

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.project_binar.kbg.R
import com.project_binar.kbg.util.SuitPrefs

class MyBattleSoundService : Service() {
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var backsound: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        suitPrefs = SuitPrefs(this)
//        Toast.makeText(this,"Music Service Created",Toast.LENGTH_SHORT).show()
        backsound = MediaPlayer.create(this, R.raw.gameplay_song)
        backsound.isLooping = true // Set looping
        if (suitPrefs.onoffsound) {
            backsound.setVolume(1F, 1F)
            backsound.start()
        }else {
            backsound.setVolume(0f,0f)
            backsound.start()
        }
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (suitPrefs.onoffsound) {
//            backsound.start()
//        }
//        Toast.makeText(this,"Music Service Started",Toast.LENGTH_SHORT).show()
        return 1
    }

    override fun onDestroy() {
        backsound.stop()
        backsound.reset()
        backsound.release()
    }

    override fun onStart(intent: Intent?, startId: Int) {

    }

    override fun onLowMemory() {

    }
}