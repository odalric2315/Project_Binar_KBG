package com.com.dagger.projecbinar_kbr

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.com.dagger.projecbinar_kbr.databinding.ActivityTutorialBinding
import com.com.dagger.projecbinar_kbr.databinding.DialogEtNamaBinding
import com.com.dagger.projecbinar_kbr.databinding.DialogGameresultBinding

class TutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding
    private lateinit var player: String
    private lateinit var cpu:String
    private lateinit var playerName:String
    private lateinit var hasil:String
    private val choice= arrayOf("batu","gunting","kertas")
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Nama Player/User db

        playerName="Nama"


        binding.buttonCloseTutorialpage.setOnClickListener {
            toHome()
        }

        binding.imgBatuPlayerTutorialpage.setOnClickListener {
            player="batu"
            binding.imgBatuPlayerTutorialpage.background=getDrawable(R.drawable.btn_hand_background)
            game(player)
        }

        binding.imgKertasPlayerTutorialpage.setOnClickListener {
            player="kertas"
            binding.imgKertasPlayerTutorialpage.background=getDrawable(R.drawable.btn_hand_background)
            game(player)
        }

        binding.imgGuntingPlayerTutorialpage.setOnClickListener {
            player="gunting"
            binding.imgGuntingPlayerTutorialpage.background=getDrawable(R.drawable.btn_hand_background)
            game(player)
        }

    }
    private fun game(player: String){
        cpu=choice.random()
        Toast.makeText(this,"CPU memilih $cpu",Toast.LENGTH_SHORT).show()
        if (player == cpu) {
            hasil="Draw!"
            showResultDialog()
        } else {
            if ((player == "batu" && cpu == "gunting") || (player == "kertas" && cpu == "batu") || (player == "gunting" && cpu == "kertas")) {
                hasil="$playerName \nWON!"
                showResultDialog()
            } else {
                hasil="CPU \nWON!"
                showResultDialog()
            }

        }
    }
    private fun showResultDialog(){
        val builder = AlertDialog.Builder(this)
        val view = DialogGameresultBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog=builder.create()
        view.textHasilgameTutorialpage.setText(hasil)
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

    private fun toHome(){
        val intent=Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
}