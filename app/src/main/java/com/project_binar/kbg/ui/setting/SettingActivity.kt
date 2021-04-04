@file:Suppress("DEPRECATION")

package com.project_binar.kbg.ui.setting

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager.STREAM_MUSIC
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.AboutUsBinding
import com.project_binar.kbg.databinding.ActivitySettingBinding
import com.project_binar.kbg.util.SuitPrefs

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var suitPrefs: SuitPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        suitPrefs = SuitPrefs(this)
        if (suitPrefs.onoffsound){
            playBackgroundMusic()
        }
        if (suitPrefs.darktheme) {
            binding.swtDarktheme.isChecked = true
        }
        if (suitPrefs.onoffsound) {
            binding.swtOnoffsound.isChecked = true
        }

        binding.btnAboutus.setOnClickListener {
            showAboutUsDialog()
        }

        binding.btnBacksetting.setOnClickListener {
            finish()
        }

        binding.swtDarktheme.apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        suitPrefs.darktheme = true
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        suitPrefs.darktheme = false
                    }
                }
            }
        }

        binding.swtOnoffsound.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                playBackgroundMusic()
                suitPrefs.onoffsound = true
            } else {
                stopBackgroundMusic()
                suitPrefs.onoffsound = false
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        playBackgroundMusic()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        stopBackgroundMusic()
    }

    fun playBackgroundMusic() {
        startService(Intent(this, MySoundService::class.java))
    }

    fun stopBackgroundMusic() {
        stopService(Intent(this, MySoundService::class.java))
    }


    private fun showAboutUsDialog() {
        val builder = AlertDialog.Builder(this)
        val view = AboutUsBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        view.ivBackaboutus.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    //Fullscreen
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

