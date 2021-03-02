package com.project_binar.kbg.ui.Multiplayer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ResourcesCompat
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.ActivityMultiplayerBinding
import com.project_binar.kbg.databinding.DialogGameresultBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.login.LoginActivity

class MultiplayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiplayerBinding
    private lateinit var player1: String
    private lateinit var player2: String
    private lateinit var playerName: String
    private lateinit var hasil: String
    private var playerWin: Int = 0
    private var playerLose: Int = 0
    private var lifePlayer1: Int = 3
    private var lifePlayer2: Int = 3
    private var dataPlayer: Player? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataPlayer = intent.getParcelableExtra<Player>(LoginActivity.DATA_PLAYER)
        binding.player1Name.text = dataPlayer?.nama
        playerName = binding.player1Name.text.toString().trim()

        //ambil Win and Lose stats dari database

        dataPlayer?.apply {
            win?.let { playerWin = it }
            lose?.let { playerLose = it }
        }

        binding.buttonClose.setOnClickListener {
            toHome()
        }
        binding.buttonRefresh.setOnClickListener {
            refresh()
        }

        binding.imgBatuPlayer2.isClickable = false
        binding.imgKertasPlayer2.isClickable = false
        binding.imgGuntingPlayer2.isClickable = false

        binding.imgBatuPlayer1.setOnClickListener {
            player1ButtonClick()
            player2ButtonClick()
            player1 = getString(R.string.title_stone)
            binding.imgBatuPlayer1.background =
                getDrawable(R.drawable.btn_hand_background)
            playerTwoPick(player1)
        }

        binding.imgKertasPlayer1.setOnClickListener {
            player1ButtonClick()
            player2ButtonClick()
            player1 = getString(R.string.title_paper)
            binding.imgKertasPlayer1.background =
                getDrawable(R.drawable.btn_hand_background)
            playerTwoPick(player1)
        }

        binding.imgGuntingPlayer1.setOnClickListener {
            player1ButtonClick()
            player2ButtonClick()
            player1 = getString(R.string.title_scissor)
            binding.imgGuntingPlayer1.background =
                getDrawable(R.drawable.btn_hand_background)
            playerTwoPick(player1)
        }

    }

    //////////////////////////////////////////////////

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playerTwoPick(player1: String) {
        binding.imgBatuPlayer2.setOnClickListener {
            player2 = getString(R.string.title_stone)
            binding.imgBatuPlayer2.background = getDrawable(R.drawable.btn_hand_background)
            checkResult(player1, player2)
        }
        binding.imgKertasPlayer2.setOnClickListener {
            player2 = getString(R.string.title_paper)
            binding.imgKertasPlayer2.background = getDrawable(R.drawable.btn_hand_background)
            checkResult(player1, player2)
        }
        binding.imgGuntingPlayer2.setOnClickListener {
            player2 = getString(R.string.title_scissor)
            binding.imgGuntingPlayer2.background = getDrawable(R.drawable.btn_hand_background)
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
        Toast.makeText(this, "Player 2 memilih $player2", Toast.LENGTH_SHORT).show()
        if (player1 == player2) {
            hasil = getString(R.string.status_result_draw)
            refresh()
        } else {
            if ((player1 == getString(R.string.title_stone) && player2 == getString(R.string.title_scissor))
                || (player1 == getString(R.string.title_paper) && player2 == getString(R.string.title_stone))
                || (player1 == getString(R.string.title_scissor) && player2 == getString(R.string.title_paper))
            ) {
                lifePlayer2--
                lifeIndicator()
                refresh()
                if (lifePlayer1 == 0 || lifePlayer2 == 0) {
                    hasil = "$playerName \nWON!"
                    playerWin++

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //Save Win/Lose ke database disini
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    showResultDialog()
                }
            } else {
                lifePlayer1--
                lifeIndicator()
                refresh()
                if (lifePlayer1 == 0 || lifePlayer2 == 0) {
                    hasil = "Player 2 \nWON!"
                    playerLose++

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //Save Win/Lose ke database disini
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    showResultDialog()
                }
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

    /////////////////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showResultDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        val view = DialogGameresultBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        view.textHasilgameTutorialpage.text = hasil
        view.buttonMainlagi.setOnClickListener {
            //Save Win/Lose ke database disini


            lifePlayer1 = 3
            lifePlayer2 = 3
            binding.ivHati3player1.visibility = View.VISIBLE
            binding.ivHati2player1.visibility = View.VISIBLE
            binding.ivHati1player2.visibility = View.VISIBLE
            binding.ivHati2player2.visibility = View.VISIBLE
            refresh()
            dialog.dismiss()
        }

        view.buttonKemenu.setOnClickListener {
            //Save Win/Lose ke database disini
            toHome()
            dialog.dismiss()
        }
        dialog.show()
    }

    ////////////////////////////////////////////////
    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}

