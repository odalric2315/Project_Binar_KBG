@file:Suppress("DEPRECATION")

package com.project_binar.kbg.ui.splash

import android.graphics.drawable.Drawable
import android.media.AudioManager.*
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.ActivitySplashScreenBinding
import com.project_binar.kbg.presenter.splash.SplashPresenterImp

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreenActivity : AppCompatActivity(), SplashView {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var audioBackground: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        audioBackground = MediaPlayer.create(this, R.raw.splashscreen_song)
        audioBackground.setLooping(false)
        audioBackground.setVolume(1F, 1F)
        audioBackground.start()

        val splashContractImpl = SplashPresenterImp(this@SplashScreenActivity, this)

        splashContractImpl.apply {
            checkStatus()

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                getActiveTheme()
            }
        }
    }

    override fun showLoading(isActive: Boolean) {
        binding.progressBar.visibility = if (isActive) View.VISIBLE else View.GONE
    }

    override fun setImageDrawable(drawable: Drawable) {
        binding.splashScreenImage.setImageDrawable(drawable)
    }



    override fun onDestroy() {
        super.onDestroy()
        showLoading(false)
        audioBackground.release()
    }
}

