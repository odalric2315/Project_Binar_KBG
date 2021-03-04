package com.project_binar.kbg.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.project_binar.kbg.databinding.AboutUsBinding
import com.project_binar.kbg.databinding.ActivitySettingBinding
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.util.SuitPrefs

class SettingActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var darkTheme: SharedPreferences
//    private var switch: Switch? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        suitPrefs = SuitPrefs(this)
        darkTheme = suitPrefs.appSettingPrefs

        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        binding.btnBacksetting.setOnClickListener {
            toHome()
        }

        binding.btnAboutus.setOnClickListener {
            showAboutusDialog()
        }


//        if (suitPrefs.loadDarkMode() == true){
//            setTheme(R.style.DarkTheme)
//        }else{
//            setTheme(R.style.AppTheme)
//        }
//        switch = findViewById<View>(R.id.swt_darktheme) as Switch?
//        switch = binding.swtDarktheme as Switch?
////        binding.swtDarktheme.isChecked as Switch
////        switch = binding.swtDarktheme.findViewById(0) as Switch
//        if (suitPrefs.loadDarkMode() == true){
//            switch!!.isChecked = true
//        }

//        darkTheme = suitPrefs.appSettingPrefs

//        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
//        val sharedPrefEdit: SharedPreferences.Editor = appSettingPrefs.edit()
//        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
//
////        if (isNightModeOn){
////            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
////            binding.swtDarktheme.isChecked
////        } else {
////            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
////        }
//        switch!!.setOnCheckedChangeListener {_, isChecked ->
//        binding.swtDarktheme.setOnCheckedChangeListener {_, isChecked ->
//            if (isChecked){
//                suitPrefs.appSettingPrefs(true)
////                restartApplication()
//
//            } else{
//                suitPrefs.appSettingPrefs(false)
//                restartApplication()
//            }
//        }
        binding.swtDarktheme.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefEdit.putBoolean("NightMode", true)
                sharedPrefEdit.apply()
                return@setOnClickListener
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefEdit.putBoolean("NightMode", false)
                sharedPrefEdit.apply()
            }
        }
    }

//    private fun restartApplication(){
//        val intent = Intent(applicationContext,SettingActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

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

