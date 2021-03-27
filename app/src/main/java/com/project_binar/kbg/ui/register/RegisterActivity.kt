package com.project_binar.kbg.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project_binar.kbg.model.register.RegisterBody
import com.project_binar.kbg.R
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.databinding.ActivityRegisterBinding
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.login.LoginActivity
import com.project_binar.kbg.util.SuitViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    //    private lateinit var databaseSuit: SuitDb
    private lateinit var viewModel: RegisterViewModel

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        databaseSuit = SuitDb.getInstance(this)
        val repository = RemoteRepository(ApiClient.service())
        val registerFactory = SuitViewModelFactory(repository)
        viewModel = ViewModelProvider(this, registerFactory).get(RegisterViewModel::class.java)
        binding.buttonRegistrasiRegisterpage.setOnClickListener {

            val email = binding.etEmailRegisterpage.text.toString().trim()
            val username = binding.etUsernameRegisterpage.text.toString().trim()
            val password = binding.etPasswordRegisterpage.text.toString().trim()
//            val player = Player(nama = name, username = username, password = password)
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (username.length >= 6 && email.length >= 6 && password.length >= 6)  {
                    val registerBody = RegisterBody(email, password, username)

                    viewModel.register(registerBody)
                    binding.progressBar.visibility= View.VISIBLE
                    binding.vlinearLayoutRegisterpage.visibility= View.GONE
                    binding.buttonLoginPage.visibility= View.GONE

                } else {
                    binding.etEmailRegisterpage.backgroundTintList =
                        ColorStateList.valueOf(R.color.red)
                    binding.etEmailRegisterpage.setHintTextColor(R.color.red)
                    binding.etUsernameRegisterpage.backgroundTintList =
                        ColorStateList.valueOf(R.color.red)
                    binding.etUsernameRegisterpage.setHintTextColor(R.color.red)
                    binding.etPasswordRegisterpage.backgroundTintList =
                        ColorStateList.valueOf(R.color.red)
                    binding.etPasswordRegisterpage.setHintTextColor(R.color.red)
                }

                //Implementasi Database
//                GlobalScope.launch {
//                    val result = databaseSuit.playerDao().addPlayer(player)
//                    if (!result.equals(-1)) {
//                        runOnUiThread {
//                            Toast.makeText(
//                                this@RegisterActivity,
//                                getString(R.string.status_register_success),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            binding.etEmailRegisterpage.setText("")
//                            binding.etPasswordRegisterpage.setText("")
//                            binding.etUsernameRegisterpage.setText("")
//                        }
//                        toLogin()
//                    } else {
//                        launch(Dispatchers.Main) {
//                            Toast.makeText(
//                                this@RegisterActivity,
//                                getString(R.string.status_register_failed),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
            } else {
                Toast.makeText(this, getString(R.string.status_data_not_null), Toast.LENGTH_SHORT)
                    .show()
            }

        }
        viewModel._dataRegister.observe(this, {
            if (it.success == true) {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.progressBar.visibility= View.GONE
                    binding.vlinearLayoutRegisterpage.visibility= View.VISIBLE
                    binding.buttonLoginPage.visibility= View.VISIBLE
                    toLogin()
                },500)
            } else {
                Toast.makeText(this, "Gagal Register", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility= View.GONE
                binding.vlinearLayoutRegisterpage.visibility= View.VISIBLE
                binding.buttonLoginPage.visibility= View.VISIBLE
            }
        })
        binding.buttonLoginPage.setOnClickListener {
            toLogin()
        }
    }

    fun toLogin() {
        binding.etEmailRegisterpage.setText("")
        binding.etPasswordRegisterpage.setText("")
        binding.etUsernameRegisterpage.setText("")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}