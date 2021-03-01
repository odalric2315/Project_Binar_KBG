package com.project_binar.kbg.ui.splash

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.databinding.ActivitySplashScreenBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreenActivity : AppCompatActivity(), SplashContract.SplashView {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashContractImpl = SplashContractImpl(this@SplashScreenActivity, this)

        splashContractImpl.checkStatus()
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
    }
}