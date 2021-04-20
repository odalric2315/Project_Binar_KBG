package com.project_binar.kbg.ui.lending_page

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.project_binar.kbg.R

class LandingPageBGM: Service() {
    private lateinit var bgm: MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        bgm = MediaPlayer.create(this, R.raw.landing_pages_song)
        bgm.isLooping = true
        bgm.setVolume(1F,1F)
        bgm.start()
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return 1
    }

    override fun onDestroy() {
        bgm.stop()
        bgm.reset()
        bgm.release()
    }
    override fun onStart(intent: Intent?, startId: Int) {

    }

    override fun onLowMemory() {

    }

}