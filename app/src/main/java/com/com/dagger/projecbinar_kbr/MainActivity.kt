package com.com.dagger.projecbinar_kbr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.com.dagger.projecbinar_kbr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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
                        binding.btnLeft.visibility = View.GONE
                    }
                    (adapter.itemCount - 1) -> {
                        binding.btnRight.visibility = View.GONE
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
                    binding.viewPager.currentItem = currentPosition
                }
            }
        })
    }
}