package com.project_binar.kbg.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.R
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityRegisterBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseSuit: SuitDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseSuit = SuitDb.getInstance(this)


        binding.buttonRegistrasiRegisterpage.setOnClickListener {

            val name = binding.etNameRegisterpage.text.toString().trim()
            val username = binding.etUsernameRegisterpage.text.toString().trim()
            val password = binding.etPasswordRegisterpage.text.toString().trim()
            val player = Player(nama = name, username = username, password = password)
            if (username.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {

                //Implementasi Database
                GlobalScope.launch {
                    val result = databaseSuit.playerDao().addPlayer(player)
                    if (!result.equals(-1)) {
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
                                getString(R.string.status_register_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.etNameRegisterpage.setText("")
                            binding.etPasswordRegisterpage.setText("")
                            binding.etUsernameRegisterpage.setText("")
                        }
                        toLogin()
                    } else {
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                this@RegisterActivity,
                                getString(R.string.status_register_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.status_data_not_null), Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.buttonLoginPage.setOnClickListener {
            toLogin()
        }
    }

    fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}