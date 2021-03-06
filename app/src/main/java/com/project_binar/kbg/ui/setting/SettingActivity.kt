package com.project_binar.kbg.ui.setting

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

