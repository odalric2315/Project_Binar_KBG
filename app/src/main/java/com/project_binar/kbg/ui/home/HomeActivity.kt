package com.project_binar.kbg.ui.home

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.project_binar.kbg.databinding.ActivityHomeBinding
import com.project_binar.kbg.databinding.EditDialogBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.textNamaHomepage.text.toString().trim()

        //tombol profile pic
        binding.imgProfileHomepage.setOnClickListener {
            val drawer = binding.drawerLayoutHomepage
            drawer.openDrawer(Gravity.START)
        }

        //tombol edit nama
        binding.imgEditnamaHomepage.setOnClickListener {
            showEditDialog(name)
        }

        //tombol logout
        binding.buttonLogoutHomepage.setOnClickListener {
            //toLogin()
        }

        //tombol multiplayer
        binding.buttonMultiplayerHomepage.setOnClickListener {
            //toMultiplayerGame()
        }

        //tombol leaderboard
        binding.buttonLeaderboardHomepage.setOnClickListener {
            //toLeaderboard()
        }

        //tombol tutorial
        binding.buttonTutorialHomepage.setOnClickListener {
            //toTutorial()
        }

        //tombol setting
        binding.buttonSettingHomepage.setOnClickListener {
            //toSettings()
        }

    }

    private fun showEditDialog(nama: String) {
        val builder = AlertDialog.Builder(this)
        val view = EditDialogBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog = builder.create()

        view.etNamaDialog.setText(nama)

        view.buttonCancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        view.buttonSaveDialog.setOnClickListener {
            view.etNamaDialog.text.toString().trim()
            //implementasi db

            dialog.dismiss()
        }
        dialog.show()
    }

    /*private fun toMultiplayerGame(){
        val intent = Intent(this,MultiplayerGameActivity::class.java)
        startActivity(intent)
    }

    private fun toLeaderboard(){
        val intent = Intent(this,LeaderboardActivity::class.java)
        startActivity(intent)
    }

    private fun toTutorial(){
        val intent = Intent(this,TutorialActivity::class.java)
        startActivity(intent)
    }

    private fun toSettings(){
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun toLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun toProfile(){
        val intent = Intent(this,ProfileActivity::class.java)
        startActivity(intent)
    }*/

}