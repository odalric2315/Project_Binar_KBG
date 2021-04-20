package com.project_binar.kbg.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project_binar.kbg.R
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.databinding.ActivityHomeBinding
import com.project_binar.kbg.databinding.DialogMenuVsChoiceBinding
import com.project_binar.kbg.databinding.DialogVideoBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.model.login.LoginBody
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.leaderboard.LeaderBoardActivity
import com.project_binar.kbg.ui.login.LoginActivity
import com.project_binar.kbg.ui.multiplayer.MultiPlayerActivity
import com.project_binar.kbg.ui.profile.ProfileActivity
import com.project_binar.kbg.ui.setting.MySoundService
import com.project_binar.kbg.ui.setting.SettingActivity
import com.project_binar.kbg.ui.tutorial.TutorialActivity
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    //    private lateinit var presenterImp: HomePresenterImp
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var viewModel: HomeViewModel
    private var dataPlayer: Player? = null

    companion object {
        const val DATA_PLAYER = "data_player"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = RemoteRepository(ApiClient.service())
        suitPrefs = SuitPrefs(this)

        val SuitViewModelFactory = SuitViewModelFactory(repository)
        viewModel = ViewModelProvider(this, SuitViewModelFactory).get(HomeViewModel::class.java)
        viewModel.getProfile(suitPrefs.token!!)
        binding.linearLayout2.visibility = View.GONE
        viewModel.getDataProfile.observe(this, {
            binding.textNamaHomepage.text = it.username
            Glide.with(this).load(it.photo).circleCrop().fitCenter()
                .into(binding.imgProfileHomepage)
            binding.progressBar2.visibility = View.GONE
            binding.linearLayout2.visibility = View.VISIBLE

        })
        viewModel.getError.observe(this, {
            val loginBody = LoginBody(suitPrefs.email, suitPrefs.password)
            viewModel.login(loginBody)
        })
        viewModel.getDataLogin.observe(this, {
            suitPrefs.token = "Bearer ${it.token}"
            viewModel.getProfile(suitPrefs.token!!)
        })
        viewModel.getErrorLogin.observe(this, {
            toLogin()
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
            finish()
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
        binding.buttonVideoHomepage.setOnClickListener {
            stopBackgroundSound()
            showVideoDialog()
        }

        //tombol setting
        binding.buttonSettingHomepage.setOnClickListener {
            //toSettings()
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        stopBackgroundSound()
    }
    override fun onRestart() {
        super.onRestart()
        PlayBackgroundSound()
    }

    override fun onResume() {
        super.onResume()
        PlayBackgroundSound()
    }


//    override fun viewPlayer(player: Player?) {
//        runOnUiThread {
//            binding.textNamaHomepage.text = player?.nama
//        }
//        dataPlayer = player
//    }

    fun PlayBackgroundSound() {
        val intent = Intent(this, MySoundService::class.java)
        startService(intent)
    }

    fun stopBackgroundSound() {
        val intent = Intent(this, MySoundService::class.java)
        stopService(intent)
    }

    private fun toLogin() {
        stopBackgroundSound()
        suitPrefs.clearSharePref()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

    private fun showPlayChoiceDialog() {
        val builder = AlertDialog.Builder(this)
        val view = DialogMenuVsChoiceBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        view.buttonPlayMultiplayer.setOnClickListener {
            toMultiplayerGame()
            dialog.dismiss()
        }
        view.buttonPlayVscpu.setOnClickListener {
            toTutorial()
            dialog.dismiss()
        }
    }

    private fun showVideoDialog() {
        val builder = AlertDialog.Builder(this)
        val view = DialogVideoBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        val videoPath = "android.resource://$packageName/${R.raw.video_tutorial}"
        var videoStatus = true
        val videoUri = Uri.parse(videoPath)
        view.videoView.setVideoURI(videoUri)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.playButton.setImageResource(R.drawable.ic_baseline_pause_button)
        view.videoView.start()
        view.videoView.setOnCompletionListener {
            it.seekTo(0)
            videoStatus = false
            view.videoView.pause()
            view.playButton.setImageResource(R.drawable.ic_baseline_play_button)
        }
        view.playButton.setOnClickListener {
            if (videoStatus) {
                videoStatus = false
                view.videoView.pause()
                view.playButton.setImageResource(R.drawable.ic_baseline_play_button)
            } else {
                videoStatus = true
                view.playButton.setImageResource(R.drawable.ic_baseline_pause_button)
                view.videoView.start()
            }
        }
        dialog.show()
        dialog.setOnDismissListener {
            if (suitPrefs.onoffsound) {
                PlayBackgroundSound()
            }
        }
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