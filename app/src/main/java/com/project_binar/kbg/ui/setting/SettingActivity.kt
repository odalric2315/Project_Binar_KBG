package com.project_binar.kbg.ui.setting

import android.media.AudioAttributes
import android.media.AudioManager
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
    private lateinit var soundPool: SoundPool
    private var loaded = false
    private var sound1 = 0
    private var streamId = 0
    private lateinit var suitPrefs: SuitPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        suitPrefs = SuitPrefs(this)

        binding.btnAboutus.setOnClickListener {
            showAboutUsDialog()
        }

        binding.btnBacksetting.setOnClickListener {
            finish()
        }

        binding.swtDarktheme.apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                isChecked = suitPrefs.darktheme
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

        binding.swtOnoffsound.apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                loaded = suitPrefs.onoffsound
                setOnCheckedChangeListener { _, isChecked ->
                    if (loaded){
                        streamId = soundPool.play(sound1, 1F,1F,1, -1, 1f)
                    } else{
                    soundPool.stop(streamId)
                    }
                }
                initializedSoundPool()
            }
        }
    }

    private fun initializedSoundPool() {
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
        sound1 = soundPool.load(this, R.raw.backsound, 1)
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

