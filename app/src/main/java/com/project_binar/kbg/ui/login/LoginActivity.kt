package com.project_binar.kbg.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project_binar.kbg.R
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
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
            binding.progressBar3.visibility= View.GONE
            binding.vlinearLayoutLoginpage.visibility=View.GONE
            binding.buttonRegisterPage.visibility=View.GONE
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
////            }
//            binding.etPasswordLoginpage.setText("")
//            binding.etEmailLoginpage.setText("")

        }
        viewModel.getDataLogin.observe(this,{
            suitPrefs.token = "Bearer ${it.token}"
            suitPrefs.login = true
            suitPrefs.email=it.email
            suitPrefs.username=it.username
            suitPrefs.password=binding.etPasswordLoginpage.text.toString().trim()
            toHome()
        })
        viewModel.getError.observe(this,{
            Toast.makeText(this,"Salah Email/Password",Toast.LENGTH_SHORT).show()
            binding.progressBar3.visibility=View.GONE
            binding.vlinearLayoutLoginpage.visibility=View.VISIBLE
            binding.buttonRegisterPage.visibility=View.VISIBLE
            binding.etEmailLoginpage.backgroundTintList= ColorStateList.valueOf(R.color.red)
            binding.etPasswordLoginpage.backgroundTintList= ColorStateList.valueOf(R.color.red)
            binding.etEmailLoginpage.setText("")
            binding.etPasswordLoginpage.setText("")

        })

        binding.buttonRegisterPage.setOnClickListener {
            toRegister()
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