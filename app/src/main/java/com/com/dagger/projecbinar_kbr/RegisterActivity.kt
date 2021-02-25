package com.com.dagger.projecbinar_kbr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.com.dagger.projecbinar_kbr.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegistrasiRegisterpage.setOnClickListener {

            val name = binding.etNameRegisterpage.text.toString().trim()
            val username = binding.etUsernameRegisterpage.text.toString().trim()
            val password = binding.etPasswordRegisterpage.text.toString().trim()

            if (!username.equals("") && !name.equals("") && !password.equals("")) {

                //Implementasi Database


                Toast.makeText(this, "Registrasi sukses silahkan login", Toast.LENGTH_SHORT).show()
                toLogin()

            }else {
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