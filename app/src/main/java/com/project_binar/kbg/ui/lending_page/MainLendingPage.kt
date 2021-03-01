package com.project_binar.kbg.ui.lending_page

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.project_binar.kbg.databinding.ActivityMainBinding
import com.project_binar.kbg.ui.register.RegisterActivity

class MainLendingPage : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                var currentPosition = position

                when (currentPosition) {
                    0 -> {
                        binding.btnLeft.visibility = View.INVISIBLE
                    }
                    else -> {
                        binding.btnLeft.visibility = View.VISIBLE
                        binding.btnRight.visibility = View.VISIBLE
                    }
                }

                binding.btnLeft.setOnClickListener {
                    currentPosition--
                    binding.viewPager.currentItem = currentPosition
                }

                binding.btnRight.setOnClickListener {
                    currentPosition++

                    if (currentPosition == 3) {
                        goToRegister()
                    } else {
                        binding.viewPager.currentItem = currentPosition
                    }
                }
            }
        })
    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}