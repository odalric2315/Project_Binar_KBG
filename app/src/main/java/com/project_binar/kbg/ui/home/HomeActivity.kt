package com.project_binar.kbg.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.databinding.ActivityHomeBinding
import com.project_binar.kbg.databinding.DialogMenuVsChoiceBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.multiplayer.MultiPlayerActivity
import com.project_binar.kbg.ui.leaderboard.LeaderBoardActivity
import com.project_binar.kbg.ui.login.LoginActivity
import com.project_binar.kbg.ui.profile.ProfileActivity
import com.project_binar.kbg.ui.setting.SettingActivity
import com.project_binar.kbg.ui.tutorial.TutorialActivity
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory

class HomeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding
//    private lateinit var presenterImp: HomePresenterImp
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var viewModel: HomeViewModel
    private var dataPlayer: Player? = null

    companion object{
        const val DATA_PLAYER = "data_player"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = RemoteRepository(ApiClient.service())
        suitPrefs = SuitPrefs(this)
        val SuitViewModelFactory = SuitViewModelFactory(repository)
        viewModel=ViewModelProvider(this,SuitViewModelFactory).get(HomeViewModel::class.java)
        viewModel.getProfile(suitPrefs.token!!)
        viewModel.getDataProfile.observe(this,{
            binding.textNamaHomepage.text=it.username
            Glide.with(this).load(it.photo).centerCrop().into(binding.imgProfileHomepage)
        })
//
//        dataPlayer = suitPrefs.getPlayer()
//
//        val playerDb = SuitDb.getInstance(this)
//        presenterImp = HomePresenterImp(this, playerDb.playerDao())
//        dataPlayer?.id?.let { presenterImp.getSinglePlayer(it) }

        //tombol profile pic
        binding.imgProfileHomepage.setOnClickListener {
//            val drawer = binding.drawerLayoutHomepage
//            drawer.openDrawer(Gravity.START)
            toProfile()
        }
        binding.textNamaHomepage.setOnClickListener {
            toProfile()
        }


        //tombol logout
        binding.buttonLogoutHomepage.setOnClickListener {
            toLogin()
        }

        //tombol main
        binding.buttonPlayHomepage.setOnClickListener {
            showPlayChoiceDialog()
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
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

//    override fun viewPlayer(player: Player?) {
//        runOnUiThread {
//            binding.textNamaHomepage.text = player?.nama
//        }
//        dataPlayer = player
//    }

    private fun toLogin() {
        suitPrefs.clearSharePref()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun toTutorial() {
        val intent = Intent(this, TutorialActivity::class.java).apply {
            this.putExtra(DATA_PLAYER, dataPlayer)
        }
        startActivity(intent)
    }

    private fun toLeaderboard() {
        val intent = Intent(this, LeaderBoardActivity::class.java)
        startActivity(intent)
    }

//    override fun onResume() {
//        super.onResume()
//        dataPlayer?.id?.let { presenterImp.getSinglePlayer(it) }
//    }

    private fun toMultiplayerGame() {
        val intent = Intent(this, MultiPlayerActivity::class.java).apply {
            this.putExtra(DATA_PLAYER, dataPlayer)
        }
        startActivity(intent)
    }

    /*private fun toSettings(){
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }*/

    private fun toProfile() {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            this.putExtra(DATA_PLAYER, dataPlayer)
        }
        startActivity(intent)
    }

    private fun showPlayChoiceDialog(){
        val builder = AlertDialog.Builder(this)
        val view = DialogMenuVsChoiceBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        dialog.show()
        view.buttonPlayMultiplayer.setOnClickListener {
            toMultiplayerGame()
            dialog.dismiss()
            finish()
        }
        view.buttonPlayVscpu.setOnClickListener {
            toTutorial()
            dialog.dismiss()
            finish()
        }
    }
}