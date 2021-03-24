package com.project_binar.kbg.ui.leaderboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project_binar.kbg.adapter.PlayerAdapter
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityLeaderboardBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.presenter.leaderboard.LeaderBoardPresenterImp
import com.project_binar.kbg.ui.profile.ProfileActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeaderBoardActivity : AppCompatActivity(), LeaderBoardView {
    private lateinit var binding: ActivityLeaderboardBinding
    private val playerData: MutableList<Player> = mutableListOf()
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter: LeaderBoardPresenterImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = SuitDb.getInstance(this)

        presenter = LeaderBoardPresenterImp(this, database.playerDao())
        presenter.getData()

        /*set adapter*/
        binding.rvLeaderboard.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@LeaderBoardActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        adapter = PlayerAdapter(playerData)

        binding.rvLeaderboard.adapter = adapter

        adapter.setOnClickItemListener {
            toProfile(it)
        }

        binding.btnBackLeaderboard.setOnClickListener {
            finish()
        }
    }

    override fun showData(data: List<Player>) {
        GlobalScope.launch(Dispatchers.Main) {
            data.let {
                playerData.clear()
                playerData.addAll(it)
                adapter.notifyDataSetChanged()
            }

            Log.e("playerData", "$playerData")
        }
    }

    private fun toProfile(dataPlayer: Player) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.DATA_PLAYER, dataPlayer)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}