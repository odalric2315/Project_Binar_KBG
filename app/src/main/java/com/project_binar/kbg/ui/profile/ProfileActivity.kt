package com.project_binar.kbg.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityProfileBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.presenter.profil.ProfilPresenterImp
import com.project_binar.kbg.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity(), ProfilView {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var presenter: ProfilPresenterImp
    private var dataPlayer: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataPlayer = intent.getParcelableExtra<Player>(LoginActivity.DATA_PLAYER)

        val playerDb = SuitDb.getInstance(this)
        presenter = ProfilPresenterImp(this, playerDb.playerDao())

        binding.btnBackProfile.setOnClickListener {
            finish()
        }

        binding.btnSaveNameProfil.setOnClickListener {
            dataPlayer?.id?.let {
                presenter.updateNamePlayer(binding.etEditNameProfile.text.toString(), it)
            }
        }
        /*getDataFromDb*/
        dataPlayer?.id?.let { presenter.getSinglePlayer(it) }
    }

    override fun showUpdNamePlayer() {
        runOnUiThread {
            Toast.makeText(this, "Berhasil Update Nama Player", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1000)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun showDataPlayer(player: Player) {
        dataPlayer = player
        Log.e("tempData", "$dataPlayer")
        dataPlayer?.apply {
            nama?.let { binding.etEditNameProfile.setText(it) }
            win?.let { binding.win.text = it.toString() }
            lose?.let { binding.lose.text = it.toString() }
            winrate?.let { binding.winrate.text = "$it %" }
        }
    }
}