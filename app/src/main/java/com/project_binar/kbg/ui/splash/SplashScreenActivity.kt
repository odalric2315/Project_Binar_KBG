package com.project_binar.kbg.ui.splash

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.ActivitySplashScreenBinding
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.lending_page.MainLendingPage
import com.project_binar.kbg.util.SuitPrefs

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        suitPrefs = SuitPrefs(this)

        if (!suitPrefs.login) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainLendingPage::class.java))
                binding.progressBar.visibility = View.GONE
                finish()
            }, 2000)
            ContextCompat.getDrawable(this, R.drawable.img_gametitle)?.let { uploadImage(it) }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, HomeActivity::class.java))
                binding.progressBar.visibility = View.GONE
                finish()
            }, 2000)
        }

    }

    private fun uploadImage(url: Drawable) {
        Glide.with(this)
            .load(url)
            .into(binding.splashScreenImage)
    }
}