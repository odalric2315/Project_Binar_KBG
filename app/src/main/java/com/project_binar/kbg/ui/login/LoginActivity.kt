package com.project_binar.kbg.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project_binar.kbg.model.login.LoginBody
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityLoginBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.register.RegisterActivity
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseSuitDb: SuitDb
    private lateinit var suitPrefs: SuitPrefs
    private lateinit var viewModel: LoginViewModel
    private var player: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = RemoteRepository(ApiClient.service())
        val SuitViewModelFactory = SuitViewModelFactory(repository)
        viewModel=ViewModelProvider(this,SuitViewModelFactory).get(LoginViewModel::class.java)
        suitPrefs = SuitPrefs(this)

        databaseSuitDb = SuitDb.getInstance(this)


        binding.buttonLoginLoginpage.setOnClickListener {

            val email = binding.etEmailLoginpage.text.toString().trim()
            val password = binding.etPasswordLoginpage.text.toString().trim()
            val loginBody= LoginBody(email, password)
            viewModel.login(loginBody)

            //Implementasi database
//            GlobalScope.launch {
//                player = databaseSuitDb.playerDao().loginPlayer(email, password)
//                if (player != null) {
//                    launch(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@LoginActivity,
//                            getString(R.string.status_login_success),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    suitPrefs.login = true
//                    suitPrefs.savePlayer(player)
//                    toHome()
//                } else {
//                    launch(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@LoginActivity,
//                            getString(R.string.status_login_failed),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
            binding.etPasswordLoginpage.setText("")
            binding.etEmailLoginpage.setText("")

        }
        viewModel.getDataLogin.observe(this,{
            suitPrefs.token = "Bearer ${it.token}"
            suitPrefs.login = true
            Toast.makeText(this,"Hallo ${it.username}", Toast.LENGTH_SHORT).show()
            toHome()
        })

        binding.buttonRegisterPage.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun toHome() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            this.putExtra(HomeActivity.DATA_PLAYER, player)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}