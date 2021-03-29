package com.project_binar.kbg.ui.leaderboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project_binar.kbg.adapter.PlayerAdapter
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityLeaderboardBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.presenter.leaderboard.LeaderBoardPresenterImp
import com.project_binar.kbg.ui.history.HistoryActivity
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
        binding.vectorLeaderboard.setOnClickListener {
            toHistory()
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
    private fun toHistory(){
        startActivity(Intent(this,HistoryActivity::class.java))
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