package com.project_binar.kbg.ui.multiplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.project_binar.kbg.R
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityMultiplayerBinding
import com.project_binar.kbg.databinding.DialogGameresultBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.model.history.AddHistoryBody
import com.project_binar.kbg.presenter.multiplayer.MultiPlayerPresenterImp
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.util.PlayViewModel
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MultiPlayerActivity : AppCompatActivity(), MultiPlayerView {
    private lateinit var binding: ActivityMultiplayerBinding
    private lateinit var presenter: MultiPlayerPresenterImp
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var viewModel:PlayViewModel
    private lateinit var player1: String
    private lateinit var player2: String
    private lateinit var playerName: String
    private lateinit var hasil: String
    private lateinit var forDialog: String
    private var lifePlayer1: Int = 3
    private var lifePlayer2: Int = 3
    private var dataPlayer: Player? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = RemoteRepository(ApiClient.service())
        val suitViewModelFactory = SuitViewModelFactory(repository)
        viewModel = ViewModelProvider(this,suitViewModelFactory).get(PlayViewModel::class.java)
        dataPlayer = intent.getParcelableExtra(HomeActivity.DATA_PLAYER)
        suitPrefs = SuitPrefs(this)

        binding.player1Name.text = suitPrefs.username
        playerName = binding.player1Name.text.toString().trim()
        val playerDb = SuitDb.getInstance(this)
        presenter = MultiPlayerPresenterImp(this, playerDb.playerDao())
        //ambil Win and Lose stats dari database

        binding.buttonClose.setOnClickListener {
            toHome()
            finish()
        }

        binding.buttonRefresh.setOnClickListener {
            resetHeart()
            refresh()
        }

        binding.imgBatuPlayer2.isClickable = false
        binding.imgKertasPlayer2.isClickable = false
        binding.imgGuntingPlayer2.isClickable = false

        binding.imgBatuPlayer1.setOnClickListener {
            player1ButtonClick()
            player2ButtonClick()
            player1 = getString(R.string.title_stone)
            it.background = ContextCompat.getDrawable(this, R.drawable.btn_hand_background)
            playerTwoPick(player1)
        }

        binding.imgKertasPlayer1.setOnClickListener {
            player1ButtonClick()
            player2ButtonClick()
            player1 = getString(R.string.title_paper)
            it.background = ContextCompat.getDrawable(this, R.drawable.btn_hand_background)
            playerTwoPick(player1)
        }

        binding.imgGuntingPlayer1.setOnClickListener {
            player1ButtonClick()
            player2ButtonClick()
            player1 = getString(R.string.title_scissor)
            it.background = ContextCompat.getDrawable(this, R.drawable.btn_hand_background)
            playerTwoPick(player1)
        }
        viewModel.addHistoryData.observe(this,{
            binding.progressBar.visibility = View.GONE
            binding.gameLayout.visibility = View.VISIBLE
            showResultDialog()
        })
        viewModel.getError.observe(this,{
            binding.progressBar.visibility = View.GONE
            binding.gameLayout.visibility = View.VISIBLE
            Toast.makeText(this,"Gagal update Hasil ke Server",Toast.LENGTH_SHORT).show()
            showResultDialog()
        })


    }

    //////////////////////////////////////////////////

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playerTwoPick(player1: String) {
        binding.imgBatuPlayer2.setOnClickListener {
            player2 = getString(R.string.title_stone)
            it.background = ContextCompat.getDrawable(this, R.drawable.btn_hand_background)
            checkResult(player1, player2)
        }
        binding.imgKertasPlayer2.setOnClickListener {
            player2 = getString(R.string.title_paper)
            it.background = ContextCompat.getDrawable(this, R.drawable.btn_hand_background)
            checkResult(player1, player2)
        }
        binding.imgGuntingPlayer2.setOnClickListener {
            player2 = getString(R.string.title_scissor)
            it.background = ContextCompat.getDrawable(this, R.drawable.btn_hand_background)
            checkResult(player1, player2)
        }
    }

    ///////////////////////////////////////////////
    private fun player1ButtonClick() {
        binding.imgBatuPlayer1.isClickable = false
        binding.imgKertasPlayer1.isClickable = false
        binding.imgGuntingPlayer1.isClickable = false
    }

    private fun player2ButtonClick() {
        binding.imgBatuPlayer2.isClickable = true
        binding.imgKertasPlayer2.isClickable = true
        binding.imgGuntingPlayer2.isClickable = true
    }

    ///////////////////////////////////////////////////////////
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun refresh() {
        binding.imgBatuPlayer1.setBackgroundResource(0)
        binding.imgGuntingPlayer1.setBackgroundResource(0)
        binding.imgKertasPlayer1.setBackgroundResource(0)
        binding.imgBatuPlayer2.setBackgroundResource(0)
        binding.imgGuntingPlayer2.setBackgroundResource(0)
        binding.imgKertasPlayer2.setBackgroundResource(0)
        binding.imgBatuPlayer1.isClickable = true
        binding.imgKertasPlayer1.isClickable = true
        binding.imgGuntingPlayer1.isClickable = true
        binding.imgBatuPlayer2.isClickable = false
        binding.imgKertasPlayer2.isClickable = false
        binding.imgGuntingPlayer2.isClickable = false
    }

    ///////////////////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkResult(player1: String, player2: String) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(300)
            if (player1 == player2) {
                hasil = getString(R.string.status_result_draw)
                forDialog = "draw"
                result(hasil)
                refresh()
            } else {
                if ((player1 == getString(R.string.title_stone) && player2 == getString(R.string.title_scissor))
                    || (player1 == getString(R.string.title_paper) && player2 == getString(R.string.title_stone))
                    || (player1 == getString(R.string.title_scissor) && player2 == getString(R.string.title_paper))
                ) {
                    lifePlayer2--
                    lifeIndicator()
                    refresh()

                    hasil = "$playerName WON!"
                    forDialog = "win"
                } else {
                    lifePlayer1--
                    lifeIndicator()
                    refresh()

                    hasil = "You LOSE!"
                    forDialog = "lose"
                }

                if (lifePlayer1 == 0 || lifePlayer2 == 0) {
                    updateResult()

                } else result(hasil)
            }
        }
    }

    ////////////////////////////////////////////////////
    private fun lifeIndicator() {
        if (lifePlayer1 == 2) {
            binding.ivHati3player1.visibility = View.INVISIBLE
        }
        if (lifePlayer2 == 2) {
            binding.ivHati1player2.visibility = View.INVISIBLE
        }
        if (lifePlayer1 == 1) {
            binding.ivHati2player1.visibility = View.INVISIBLE
        }
        if (lifePlayer2 == 1) {
            binding.ivHati2player2.visibility = View.INVISIBLE
        }
    }

    private fun resetHeart() {
        binding.ivHati1player1.visibility = View.VISIBLE
        binding.ivHati2player1.visibility = View.VISIBLE
        binding.ivHati3player1.visibility = View.VISIBLE
        binding.ivHati1player2.visibility = View.VISIBLE
        binding.ivHati2player2.visibility = View.VISIBLE
        binding.ivHati3player2.visibility = View.VISIBLE
        lifePlayer1 = 3
        lifePlayer2 = 3
    }

    /////////////////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showResultDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val view = DialogGameresultBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        view.textHasilgameTutorialpage.text = hasil

        if(forDialog=="win") {
            view.vectorGameresult.setAnimation(R.raw.if_win)
        } else if(forDialog=="lose") {
            view.vectorGameresult.setAnimation(R.raw.if_lose_thunder)
        }

        dialog.show()

        //Save Win/Lose ke database disini

        view.buttonMainlagi.setOnClickListener {
            refresh()
            resetHeart()
            dialog.dismiss()
        }

        view.buttonKemenu.setOnClickListener {
            toHome()
            dialog.dismiss()
            finish()
        }
    }

    private fun updateResult() {
        var apiResult : String?
        if (lifePlayer1 != 0) {
            apiResult = "Player Win"
            val addHistoryBody = AddHistoryBody("Multiplayer",apiResult)
            binding.progressBar.visibility=View.VISIBLE
            binding.gameLayout.visibility=View.GONE
            viewModel.addHistory(suitPrefs.token!!, addHistoryBody)
            dataPlayer?.id?.let { id ->
                presenter.updateWin(1, id)
            }
        } else {
            apiResult = "Opponent Win"
            val addHistoryBody = AddHistoryBody("Multiplayer",apiResult)
            binding.progressBar.visibility=View.VISIBLE
            binding.gameLayout.visibility=View.GONE
            viewModel.addHistory(suitPrefs.token!!, addHistoryBody)
            dataPlayer?.id?.let { id -> presenter.updateLose(1, id) }
        }
    }

    ////////////////////////////////////////////////
    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun showUpdatePlayer() {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(
                this@MultiPlayerActivity,
                "Status Berhasil di Update",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun result(data: String) {
        Snackbar.make(binding.parentMultiPlayer, data, Snackbar.LENGTH_SHORT)
            .setAnimationMode(ANIMATION_MODE_SLIDE)
            .show()
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