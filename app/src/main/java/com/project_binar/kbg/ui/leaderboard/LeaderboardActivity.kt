package com.project_binar.kbg.ui.leaderboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.adapter.PlayerAdapter
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityLeaderboardBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.presenter.leaderboard.LeaderboardPresenterImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeaderboardActivity : AppCompatActivity(), LeaderboarView {
    private lateinit var binding: ActivityLeaderboardBinding
    private val playerData : MutableList<Player> = mutableListOf()
    private lateinit var adapter : PlayerAdapter
    private lateinit var presenter : LeaderboardPresenterImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = SuitDb.getInstance(this)

        presenter = LeaderboardPresenterImp(this, database.playerDao())
        presenter.getData()

        adapter = PlayerAdapter(playerData)

        binding.rvLeaderboard.adapter = adapter
    }

    override fun showData(data: List<Player>) {
        GlobalScope.launch(Dispatchers.Main) {
            data.let {
                playerData.clear()
                playerData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }


}