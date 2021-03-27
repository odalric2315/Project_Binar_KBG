package com.project_binar.kbg.presenter.splash

import android.app.Activity
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.project_binar.kbg.R
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.lending_page.LendingPageActivity
import com.project_binar.kbg.ui.splash.SplashView
import com.project_binar.kbg.util.SuitPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashPresenterImp(
    private val context: Activity,
    private val splashView: SplashView
) : SplashPresenter {
    private val prefSuitPrefs = SuitPrefs(context)
    private lateinit var soundPool: SoundPool
    private var loaded = false
    private var sound1 = 0

    override fun checkStatus() {
        GlobalScope.launch {
            delay(2000)
            if (prefSuitPrefs.login) {
                context.startActivity(Intent(context, HomeActivity::class.java))
            } else context.startActivity(Intent(context, LendingPageActivity::class.java))

            context.finish()
        }

        splashView.showLoading(true)

        ContextCompat.getDrawable(context, R.drawable.img_gametitle)?.let {
            splashView.setImageDrawable(it)
        }
    }

    override fun getActiveTheme() {
        if (prefSuitPrefs.darktheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun getActiveBacksound(){
        if (Build.VERSION.SDK_INT >= 23) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val builder = SoundPool.Builder()
            builder.setAudioAttributes(audioAttributes).setMaxStreams(1)
            soundPool = builder.build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            loaded = true
        }
        sound1 = soundPool.load(context,R.raw.splashscreen_song, 1)
    }
}

