package com.project_binar.kbg.ui.setting

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
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
}

