package com.project_binar.kbg.ui.tutorial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.ActivityTutorialBinding
import com.project_binar.kbg.databinding.DialogGameresultBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.ui.home.HomeActivity

class TutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding
    private lateinit var player: String
    private lateinit var cpu: String
    private lateinit var playerName: String
    private lateinit var hasil: String
    private var dataPlayer: Player? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Nama Player/User db
        dataPlayer = intent.getParcelableExtra<Player>(HomeActivity.DATA_PLAYER)
        binding.textPlayerNameTutorialpage.text = dataPlayer?.nama
        playerName = binding.textPlayerNameTutorialpage.text.toString()
        playerName = getString(R.string.title_name)

        binding.buttonCloseTutorialpage.setOnClickListener {
            toHome()
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

    }

    private fun game(player: String) {
        cpu = resources.getStringArray(R.array.choice).random()
        Toast.makeText(this, "CPU memilih $cpu", Toast.LENGTH_SHORT).show()
        if (player == cpu) {
            hasil = getString(R.string.status_result_draw)
            showResultDialog()
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
                showResultDialog()
            } else {
                hasil = "CPU \nWON!"
                showResultDialog()
            }

        }
    }

    private fun showResultDialog() {
        val builder = AlertDialog.Builder(this)
        val view = DialogGameresultBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()
        view.textHasilgameTutorialpage.text = hasil
        view.buttonMainlagi.setOnClickListener {
            binding.imgBatuPlayerTutorialpage.setBackgroundResource(0)
            binding.imgKertasPlayerTutorialpage.setBackgroundResource(0)
            binding.imgGuntingPlayerTutorialpage.setBackgroundResource(0)
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