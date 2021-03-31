package com.project_binar.kbg.ui.tutorial

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
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
    private var dataPlayer: Player? = null
    private lateinit var audioBackground: MediaPlayer
    private lateinit var audioWin: MediaPlayer
    private lateinit var audioLose : MediaPlayer
//    private lateinit var audioWin: SoundPool
//    private lateinit var audioLose : SoundPool
//    private var loaded = false
//    private var soundWin = 0
//    private var soundLose = 0
//    private var streamIdWin = 0
//    private var streamIdLose = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        audioBackground = MediaPlayer.create(this, R.raw.gameplay_song)
        audioWin = MediaPlayer.create(this,R.raw.winner_song)
        audioLose = MediaPlayer.create(this,R.raw.loser_song)
        audioBackground.setLooping(true)
        audioWin.setLooping(true)
        audioLose.setLooping(true)
        audioBackground.setVolume(1F, 1F)
        audioWin.setVolume(1F, 1F)
        audioLose.setVolume(1F, 1F)
        audioBackground.start()

        val repository = RemoteRepository(ApiClient.service())
        val suitViewModelFactory = SuitViewModelFactory(repository)
        suitPrefs= SuitPrefs(this)
        viewModel = ViewModelProvider(this,suitViewModelFactory).get(PlayViewModel::class.java)
        //Nama Player/User db
        dataPlayer = intent.getParcelableExtra<Player>(HomeActivity.DATA_PLAYER)
        binding.textPlayerNameTutorialpage.text = suitPrefs.username
        playerName = binding.textPlayerNameTutorialpage.text.toString()
        binding.buttonCloseTutorialpage.setOnClickListener {
            toHome()
            audioBackground.release()
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
        viewModel.addHistoryData.observe(this,{
            binding.progressBar.visibility= View.GONE
            binding.tutorialLayout.visibility=View.VISIBLE
            showResultDialog()
        })
        viewModel.getError.observe(this,{
            binding.progressBar.visibility= View.GONE
            binding.tutorialLayout.visibility=View.VISIBLE
            Toast.makeText(this,"Gagal update Hasil ke Server",Toast.LENGTH_SHORT).show()
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

        if(forDialog=="win") {
            view.vectorGameresult.setAnimation(R.raw.if_win)
            audioBackground.release()
            audioWin.start()
        } else if(forDialog=="lose") {
            view.vectorGameresult.setAnimation(R.raw.if_lose_thunder)
            audioBackground.release()
            audioLose.start()
        }

        view.buttonMainlagi.setOnClickListener {
            binding.imgBatuPlayerTutorialpage.setBackgroundResource(0)
            binding.imgKertasPlayerTutorialpage.setBackgroundResource(0)
            binding.imgGuntingPlayerTutorialpage.setBackgroundResource(0)
            audioWin.release()
            audioLose.release()
            dialog.dismiss()
            audioBackground.start()
        }

        view.buttonKemenu.setOnClickListener {
            toHome()
            dialog.dismiss()
            audioBackground.release()
            finish()
        }
        dialog.show()
    }
    private fun addHistory(){
        if (forDialog.equals("win")){
            val apiResult = "Player Win"
            val addHistoryBody = AddHistoryBody("Singleplayer",apiResult)
            binding.progressBar.visibility=View.VISIBLE
            binding.tutorialLayout.visibility=View.GONE
            viewModel.addHistory(suitPrefs.token!!,addHistoryBody)
        } else if (forDialog.equals("lose")){
            val apiResult = "Opponent Win"
            val addHistoryBody = AddHistoryBody("Singleplayer",apiResult)
            binding.progressBar.visibility=View.VISIBLE
            binding.tutorialLayout.visibility=View.GONE
            viewModel.addHistory(suitPrefs.token!!,addHistoryBody)
        }else{
            val apiResult = "Draw"
            val addHistoryBody = AddHistoryBody("Singleplayer",apiResult)
            binding.progressBar.visibility=View.VISIBLE
            binding.tutorialLayout.visibility=View.GONE
            viewModel.addHistory(suitPrefs.token!!,addHistoryBody)
        }
    }

    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
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