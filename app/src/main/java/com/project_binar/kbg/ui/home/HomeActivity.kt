package com.project_binar.kbg.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityHomeBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.ui.leaderboard.LeaderboardActivity
import com.project_binar.kbg.presenter.home.HomePresenterImp
import com.project_binar.kbg.ui.Multiplayer.MultiPlayerActivity
import com.project_binar.kbg.ui.login.LoginActivity
import com.project_binar.kbg.ui.profile.ProfileActivity
import com.project_binar.kbg.ui.tutorial.TutorialActivity
import com.project_binar.kbg.util.SuitPrefs

class HomeActivity : AppCompatActivity(), HomeView {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenterImp: HomePresenterImp
    private lateinit var suitPrefs: SuitPrefs
    private var dataPlayer: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        suitPrefs = SuitPrefs(this)

        dataPlayer = suitPrefs.getPlayer()

        val playerDb = SuitDb.getInstance(this)
        presenterImp = HomePresenterImp(this, playerDb.playerDao())
        dataPlayer?.id?.let { presenterImp.getSinglePlayer(it) }

        //tombol profile pic
        binding.imgProfileHomepage.setOnClickListener {
//            val drawer = binding.drawerLayoutHomepage
//            drawer.openDrawer(Gravity.START)
        }

        //tombol edit nama
        binding.imgEditnamaHomepage.setOnClickListener {
//            showEditDialog(name)
            toProfile()
        }

        //tombol logout
        binding.buttonLogoutHomepage.setOnClickListener {
            toLogin()
        }

        //tombol multiplayer
        binding.buttonMultiplayerHomepage.setOnClickListener {
            toMultiplayerGame()
        }

        //tombol leaderboard
        binding.buttonLeaderboardHomepage.setOnClickListener {
            toLeaderboard()
        }

        //tombol tutorial
        binding.buttonTutorialHomepage.setOnClickListener {
            toTutorial()
        }

        //tombol setting
        binding.buttonSettingHomepage.setOnClickListener {
            //toSettings()
        }

    }

    override fun viewPlayer(player: Player?) {
        runOnUiThread {
            binding.textNamaHomepage.text = player?.nama
        }
        dataPlayer = player
    }

    private fun toLogin() {
        suitPrefs.clearSharePref()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun toTutorial() {
        val intent = Intent(this, TutorialActivity::class.java).apply {
            this.putExtra(LoginActivity.DATA_PLAYER, dataPlayer)
        }
        startActivity(intent)
    }

    private fun toLeaderboard() {
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        dataPlayer?.id?.let { presenterImp.getSinglePlayer(it) }
    }

    private fun toMultiplayerGame() {
        val intent = Intent(this, MultiPlayerActivity::class.java).apply {
            this.putExtra(LoginActivity.DATA_PLAYER, dataPlayer)
        }
        startActivity(intent)
    }

    /*private fun toSettings(){
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }*/

    private fun toProfile() {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            this.putExtra(LoginActivity.DATA_PLAYER, dataPlayer)
        }
        startActivity(intent)
    }
}