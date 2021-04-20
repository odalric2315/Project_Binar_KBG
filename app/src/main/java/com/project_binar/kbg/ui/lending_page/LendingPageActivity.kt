package com.project_binar.kbg.ui.lending_page

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.ActivityLendingPageBinding
import com.project_binar.kbg.ui.register.RegisterActivity

class LendingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLendingPageBinding
    private lateinit var audioBackground: MediaPlayer
    private val position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLendingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        audioBackground = MediaPlayer.create(this, R.raw.landing_pages_song)
//        audioBackground.setLooping(true)
//        audioBackground.setVolume(1F, 1F)
//        audioBackground.start()

        val adapter = PagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                var currentPosition = position

                binding.btnLeft.visibility = if(currentPosition == 0 || currentPosition == 2){
                    View.INVISIBLE
                } else View.VISIBLE

                binding.btnLeft.setOnClickListener {
                    currentPosition--
                    binding.viewPager.currentItem = currentPosition
                }

                binding.btnRight.setOnClickListener {
                    currentPosition++

                    if (currentPosition == 3) {
//                        audioBackground.stop()
//                        audioBackground.release()
                        goToRegister()
                    } else {
                        binding.viewPager.currentItem = currentPosition
                    }
                }
            }
        })
    }

    private fun goToRegister() {
        stopService(Intent(this,LandingPageBGM::class.java))
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
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