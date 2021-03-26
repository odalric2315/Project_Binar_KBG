package com.project_binar.kbg.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityProfileBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    //    private lateinit var presenter: ProfilPresenterImp
    private lateinit var viewModel: ProfileViewModel

    companion object {
        const val DATA_PLAYER = "data_player"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = RemoteRepository(ApiClient.service())
        val SuitViewModelFactory = SuitViewModelFactory(repository)
        val suitPrefs = SuitPrefs(this)
        viewModel = ViewModelProvider(this, SuitViewModelFactory).get(ProfileViewModel::class.java)
        viewModel.getProfile(suitPrefs.token!!)
        viewModel.getDataProfile.observe(this, {
            binding.etEditNameProfile.setText(it.username)
            binding.etEditEmailProfile.setText(it.email)
            Glide.with(this).load(it.photo).fitCenter().into(binding.imageProfilePic)
            binding.progressBar.visibility= View.GONE
            binding.profileLayout.visibility=View.VISIBLE
        })
        viewModel.getError.observe(this,{
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        })

        val dataPlayer = intent.getParcelableExtra<Player>(DATA_PLAYER)
//        binding.etEditNameProfile.setText(dataPlayer?.nama)

        dataPlayer?.apply {
            win?.let { binding.win.text = it.toString() }
            lose?.let { binding.lose.text = it.toString() }
            rate?.let { binding.winrate.text = "${it.toInt()}%" }
        }
        val playerDb = SuitDb.getInstance(this)

        binding.btnBackProfile.setOnClickListener {
            val intentProfile = Intent(this, HomeActivity::class.java)
            intentProfile.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentProfile)
        }

//        binding.btnSaveNameProfil.setOnClickListener {
//            presenter = ProfilPresenterImp(this, playerDb.playerDao())
//            dataPlayer?.id?.let {
//                presenter.updateNamePlayer(binding.etEditNameProfile.text.toString(), it)
//            }
//        }
    }

//    override fun showUpdatePlayer() {
//        runOnUiThread {
//            Toast.makeText(this, "Berhasil Update Nama Player", Toast.LENGTH_SHORT).show()
//            Handler(Looper.getMainLooper()).postDelayed({
//                finish()
//            }, 1000)
//        }
//
//    }
}