package com.com.dagger.projecbinar_kbr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.com.dagger.projecbinar_kbr.databinding.ActivityRegisterBinding
import com.com.dagger.projecbinar_kbr.db.SuitDb
import com.com.dagger.projecbinar_kbr.model.Player
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
            val player = Player(null, name, username, password,null,null,null)
            if (!username.equals("") && !name.equals("") && !password.equals("")) {

                //Implementasi Database
                GlobalScope.launch {
                    val result = databaseSuit.playerDao().addPlayer(player)
                    if (!result.equals(-1)) {
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
                                "REGISTER SUCCESS",
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
                                "REGISTER FAILED",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }

        }
        binding.buttonLoginRegisterpage.setOnClickListener {
            toLogin()
        }
    }

    fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}