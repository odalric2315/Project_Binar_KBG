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
import com.project_binar.kbg.ui.home.HomeActivity

class MultiplayerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMultiplayerBinding
    private lateinit var player1Name : String
    private lateinit var player2Name : String
    private var option1: String = ""
    private var option2: String = ""
    private var state1: Boolean = false
    private var state2: Boolean = false
    private lateinit var selected1: View
    private lateinit var selected2: View

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.player1Name.text = "Nama"
        binding.player2Name.text = "Nama2"

        binding.buttonRefresh.setOnClickListener {
            refresh()
        }
        binding.buttonClose.setOnClickListener {
            toHome()
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playerOneGame() {
        binding.imgBatuPlayer1.setOnClickListener {
            playerOneSelect(it)
            if (option2 != "") {
                checkResult()
            }
        }
        binding.imgKertasPlayer1.setOnClickListener {
            playerOneSelect(it)
            if (option2 != "") {
                checkResult()
            }
        }
        binding.imgGuntingPlayer1.setOnClickListener {
            playerOneSelect(it)
            if (option2 != "") {
                checkResult()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playerTwoGame() {
        binding.imgBatuPlayer2.setOnClickListener {
            playerTwoSelect(it)
            if (option1 != "") {
                checkResult()
            }
        }
        binding.imgKertasPlayer2.setOnClickListener {
            playerTwoSelect(it)
            if (option1 != "") {
                checkResult()
            }
        }
        binding.imgGuntingPlayer2.setOnClickListener {
            playerTwoSelect(it)
            if (option1 != "") {
                checkResult()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playerOneSelect(view: View) {
        if (!state1) {
            selected1 = view
            refreshPlayerOne()
            state1 = true
            option1 = selected1.contentDescription.toString()
            view.background = getDrawable(R.drawable.btn_hand_background)
        } else {
            refreshPlayerOne()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playerTwoSelect(view: View) {
        if (!state2) {
            selected2 = view
            refreshPlayerTwo()
            state2 = true
            option2 = selected2.contentDescription.toString()
            view.background = getDrawable(R.drawable.btn_hand_background)
            Toast.makeText(this, "Player 2 memilih ${view.contentDescription}",
                Toast.LENGTH_LONG
            ).show()
        } else {
            refreshPlayerTwo()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun refresh() {
        option1 = ""
        option2 = ""
        state1 = false
        state2 = false
        selected1.background = getDrawable(R.drawable.btn_hand_background)
        selected2.background = getDrawable(R.drawable.btn_hand_background)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkResult() {
        if (option1 == option2) {
            showResultDialog("Hasil SERI")
        }
        when (option1) {
            "batu" -> {
                when (option2) {
                    "kertas" -> {
                        showResultDialog("$player2Name MENANG")
                    }
                    "gunting" -> {
                        showResultDialog("$player1Name MENANG")
                    }
                }
            }
            "kertas" -> {
                when (option2) {
                    "gunting" -> {
                        showResultDialog("$player2Name MENANG")
                    }
                    "batu" -> {
                        showResultDialog("$player1Name MENANG")
                    }
                }
            }
            "gunting" -> {
                when (option2) {
                    "batu" -> {
                        showResultDialog("$player2Name MENANG")
                    }
                    "kertas" -> {
                        showResultDialog("$player1Name MENANG")
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun refreshPlayerOne() {
        option1 = ""
        state1 = false
        selected1.background = getDrawable(R.drawable.btn_hand_background)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun refreshPlayerTwo() {
        option2 = ""
        state2 = false
        selected2.background = getDrawable(R.drawable.btn_hand_background)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showResultDialog(message: String) {
        val view = DialogGameresultBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(view.root)
        val dialog = dialogBuilder.create()
        view.textHasilgameTutorialpage.text = message
        view.buttonMainlagi.setOnClickListener {
            refresh()
            dialog.dismiss()
        }
        view.buttonKemenu.setOnClickListener {
            toHome()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}

