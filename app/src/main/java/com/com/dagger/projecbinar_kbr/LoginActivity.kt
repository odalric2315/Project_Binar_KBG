package com.com.dagger.projecbinar_kbr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.com.dagger.projecbinar_kbr.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLoginLoginpage.setOnClickListener {

            val username = binding.etUsernameLoginpage.text.toString().trim()
            val password = binding.etPasswordLoginpage.text.toString().trim()

            //Implementasi database


            toHome()
        }

        binding.buttonBacktoregisterLoginpage.setOnClickListener {
            toRegister()
        }
    }

    fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}