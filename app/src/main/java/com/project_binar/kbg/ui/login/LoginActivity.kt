package com.project_binar.kbg.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.R
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityLoginBinding
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.register.RegisterActivity
import com.project_binar.kbg.util.SuitPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseSuitDb: SuitDb
    private lateinit var suitPrefs: SuitPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseSuitDb = SuitDb.getInstance(this)
        suitPrefs = SuitPrefs(this)


        binding.buttonLoginLoginpage.setOnClickListener {

            val username = binding.etUsernameLoginpage.text.toString().trim()
            val password = binding.etPasswordLoginpage.text.toString().trim()

            //Implementasi database
            GlobalScope.launch {
                val player = databaseSuitDb.playerDao().getListPlayer(username, password)
                if (player != null) {
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.status_login_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    suitPrefs.name = player.nama
                    suitPrefs.login = true
                    toHome()
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.status_login_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            binding.etPasswordLoginpage.setText("")
            binding.etUsernameLoginpage.setText("")

        }

        binding.buttonBacktoregisterLoginpage.setOnClickListener {
            toRegister()
        }
    }

    fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}