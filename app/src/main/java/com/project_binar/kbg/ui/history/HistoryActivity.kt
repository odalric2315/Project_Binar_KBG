package com.project_binar.kbg.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project_binar.kbg.adapter.HistoryAdapter
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.databinding.ActivityHistoryBinding
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.setting.MySoundService
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel
    private lateinit var sharePreferenceHelper: SuitPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = RemoteRepository(ApiClient.service())
        val gameViewModelFactory = SuitViewModelFactory(repository)
        adapter= HistoryAdapter()
        sharePreferenceHelper = SuitPrefs(this)
        PlayBackgroundSound()

        viewModel= ViewModelProvider(this,gameViewModelFactory).get(HistoryViewModel::class.java)
        viewModel.getHistory(sharePreferenceHelper.token!!)
        binding.rvHistory.layoutManager= LinearLayoutManager(this)
        binding.rvHistory.adapter=adapter
        viewModel.getDataHistory.observe(this, {
            adapter.setData(it)
            binding.progressBar.visibility = View.GONE
        })
    }
    fun PlayBackgroundSound() {
        val intent = Intent(this, MySoundService::class.java)
        startService(intent)
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