package com.project_binar.kbg.ui.tutorial

import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project_binar.kbg.R
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.databinding.ActivityTutorialBinding
import com.project_binar.kbg.databinding.DialogGameresultBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.model.history.AddHistoryBody
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.setting.MyBattleSoundService
import com.project_binar.kbg.util.PlayViewModel
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory

class TutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding
    private lateinit var viewModel: PlayViewModel
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var player: String
    private lateinit var cpu: String
    private lateinit var playerName: String
    private lateinit var hasil: String
    private lateinit var forDialog: String
    private lateinit var soundPool: SoundPool
    private var winsfx = 0
    private var losesfx = 0
    private var streamId=0
    private var dataPlayer: Player? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playBackgroundMusic()
        val repository = RemoteRepository(ApiClient.service())
        val suitViewModelFactory = SuitViewModelFactory(repository)
        suitPrefs = SuitPrefs(this)
        initializedSoundPool()
        viewModel = ViewModelProvider(this, suitViewModelFactory).get(PlayViewModel::class.java)
        //Nama Player/User db
        dataPlayer = intent.getParcelableExtra<Player>(HomeActivity.DATA_PLAYER)
        binding.textPlayerNameTutorialpage.text = suitPrefs.username
        playerName = binding.textPlayerNameTutorialpage.text.toString()
        binding.buttonCloseTutorialpage.setOnClickListener {
            toHome()
            finish()
        }

        binding.imgBatuPlayerTutorialpage.setOnClickListener {
            player = getString(R.string.title_stone)
            binding.imgBatuPlayerTutorialpage.background =
                getDrawable(R.drawable.btn_hand_background)
            game(player)
        }

        binding.imgKertasPlayerTutorialpage.setOnClickListener {
            player = getString(R.string.title_paper)
            binding.imgKertasPlayerTutorialpage.background =
                getDrawable(R.drawable.btn_hand_background)
            game(player)
        }

        binding.imgGuntingPlayerTutorialpage.setOnClickListener {
            player = getString(R.string.title_scissor)
            binding.imgGuntingPlayerTutorialpage.background =
                getDrawable(R.drawable.btn_hand_background)
            game(player)
        }
        viewModel.addHistoryData.observe(this, {
            binding.progressBar.visibility = View.GONE
            binding.tutorialLayout.visibility = View.VISIBLE
            showResultDialog()
        })
        viewModel.getError.observe(this, {
            binding.progressBar.visibility = View.GONE
            binding.tutorialLayout.visibility = View.VISIBLE
            Toast.makeText(this, "Gagal update Hasil ke Server", Toast.LENGTH_SHORT).show()
            showResultDialog()
        })

    }

    private fun game(player: String) {
        cpu = resources.getStringArray(R.array.choice).random()
        Toast.makeText(this, "CPU memilih $cpu", Toast.LENGTH_SHORT).show()
        if (player == cpu) {
            hasil = getString(R.string.status_result_draw)
            forDialog = "draw"
            addHistory()
        } else {
            if ((player == getString(R.string.title_stone)
                        && cpu == getString(R.string.title_scissor))
                || (player == getString(
                    R.string.title_paper
                )
                        && cpu == getString(R.string.title_stone))
                || (player == getString(R.string.title_scissor)
                        && cpu == getString(R.string.title_paper))
            ) {
                hasil = "$playerName \nWON!"
                forDialog = "win"
                addHistory()
            } else {
                hasil = "CPU \nWON!"
                forDialog = "lose"
                addHistory()
            }

        }
    }

//    private fun initializedSoundPoolWin() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            val audioAttributes = AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_GAME)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build()
//            val builder = SoundPool.Builder()
//            builder.setAudioAttributes(audioAttributes).setMaxStreams(1)
//            audioWin = builder.build()
//        } else {
//            audioWin = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
//        }
//        audioWin.setOnLoadCompleteListener { _, _, _ ->
//            loaded = true
//        }
//        soundWin = audioWin.load(this, R.raw.winner_song, 1)
//    }

//    private fun initializedSoundPoolLose() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            val audioAttributes = AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_GAME)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build()
//            val builder = SoundPool.Builder()
//            builder.setAudioAttributes(audioAttributes).setMaxStreams(1)
//            audioLose = builder.build()
//        } else {
//            audioLose = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
//        }
//        audioLose.setOnLoadCompleteListener { _, _, _ ->
//            loaded = true
//        }
//        soundLose = audioLose.load(this, R.raw.loser_song, 1)
//    }

    private fun showResultDialog() {
        val builder = AlertDialog.Builder(this)
        val view = DialogGameresultBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        view.textHasilgameTutorialpage.text = hasil
        stopBackgroundMusic()
        if (forDialog == "win") {
            view.vectorGameresult.setAnimation(R.raw.if_win)
            if (suitPrefs.onoffsound){
                streamId=soundPool.play(winsfx,1f,1f,1,-1,1f)
            }
        } else if (forDialog == "lose") {
            view.vectorGameresult.setAnimation(R.raw.if_lose_thunder)
            if (suitPrefs.onoffsound){
                streamId=soundPool.play(losesfx,1f,1f,1,-1,1f)
            }
        } else {
            if (suitPrefs.onoffsound){
                streamId=soundPool.play(losesfx,1f,1f,1,-1,1f)
            }
        }

        view.buttonMainlagi.setOnClickListener {
            soundPool.stop(streamId)
            binding.imgBatuPlayerTutorialpage.setBackgroundResource(0)
            binding.imgKertasPlayerTutorialpage.setBackgroundResource(0)
            binding.imgGuntingPlayerTutorialpage.setBackgroundResource(0)
            playBackgroundMusic()
            dialog.dismiss()
        }

        view.buttonKemenu.setOnClickListener {
            soundPool.stop(streamId)
            toHome()
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    private fun addHistory() {
        if (forDialog.equals("win")) {
            val apiResult = "Player Win"
            val addHistoryBody = AddHistoryBody("Singleplayer", apiResult)
            binding.progressBar.visibility = View.VISIBLE
            binding.tutorialLayout.visibility = View.GONE
            viewModel.addHistory(suitPrefs.token!!, addHistoryBody)
        } else if (forDialog.equals("lose")) {
            val apiResult = "Opponent Win"
            val addHistoryBody = AddHistoryBody("Singleplayer", apiResult)
            binding.progressBar.visibility = View.VISIBLE
            binding.tutorialLayout.visibility = View.GONE
            viewModel.addHistory(suitPrefs.token!!, addHistoryBody)
        } else {
            val apiResult = "Draw"
            val addHistoryBody = AddHistoryBody("Singleplayer", apiResult)
            binding.progressBar.visibility = View.VISIBLE
            binding.tutorialLayout.visibility = View.GONE
            viewModel.addHistory(suitPrefs.token!!, addHistoryBody)
        }
    }

    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun playBackgroundMusic() {
        startService(Intent(this, MyBattleSoundService::class.java))
    }

    fun stopBackgroundMusic() {
        stopService(Intent(this, MyBattleSoundService::class.java))
    }


    private fun initializedSoundPool() {
        if (Build.VERSION.SDK_INT >= 21) {
            val audioAttributes =
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            val builder = SoundPool.Builder()
            builder.setAudioAttributes(audioAttributes).setMaxStreams(1)
            soundPool = builder.build()
        } else {
            soundPool= SoundPool(1,AudioManager.STREAM_MUSIC,0)
        }
        winsfx=soundPool.load(this,R.raw.winner_song,1)
        losesfx=soundPool.load(this,R.raw.loser_song,1)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        stopBackgroundMusic()
    }

    override fun onRestart() {
        super.onRestart()
        playBackgroundMusic()
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