package com.com.dagger.projecbinar_kbr

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.com.dagger.projecbinar_kbr.databinding.ActivitySplashScreenBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            binding.progressBar.visibility = View.GONE
            finish()
        }, 2000)
        uploadImage("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
    }

    private fun uploadImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.splashScreenTitle)
    }
}