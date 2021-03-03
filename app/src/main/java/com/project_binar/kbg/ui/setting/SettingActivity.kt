package com.project_binar.kbg.ui.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.project_binar.kbg.databinding.AboutUsBinding
import com.project_binar.kbg.databinding.ActivitySettingBinding
import com.project_binar.kbg.ui.home.HomeActivity

class SettingActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBacksetting.setOnClickListener {
            toHome()
        }

        binding.btnAboutus.setOnClickListener {
            showAboutusDialog()
        }

        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", true)

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.swtDarktheme.isChecked
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.swtDarktheme.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefEdit.putBoolean("NightMode", true)
                sharedPrefEdit.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefEdit.putBoolean("NightMode", false)
                sharedPrefEdit.apply()
            }
        }
    }

    private fun showAboutusDialog(){
        val builder = AlertDialog.Builder(this)
        val view = AboutUsBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        view.ivBackaboutus.setOnClickListener {
        dialog.dismiss()
        }
        dialog.show()
    }

    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}

