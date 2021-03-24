package com.com.dagger.projecbinar_kbr.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.com.dagger.projecbinar_kbr.R

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
        val gameViewModelFactory = GameViewModelFactory(repository)
        adapter= HistoryAdapter()
        sharePreferenceHelper = SuitPrefs(this)
        viewModel= ViewModelProvider(this,gameViewModelFactory).get(HistoryViewModel::class.java)
        viewModel.getHistory(sharePreferenceHelper.token!!)
        binding.rvHistory.layoutManager= LinearLayoutManager(this)
        binding.rvHistory.adapter=adapter
        viewModel.getDataHistory.observe(this, {
            adapter.setData(it)
            binding.progressBar.visibility = View.GONE
        })
    }
}