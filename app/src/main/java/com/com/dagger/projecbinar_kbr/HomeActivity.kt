package com.com.dagger.projecbinar_kbr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import com.com.dagger.projecbinar_kbr.databinding.ActivityHomeBinding
import com.com.dagger.projecbinar_kbr.databinding.DialogEtNamaBinding
import com.com.dagger.projecbinar_kbr.db.SuitDb
import com.com.dagger.projecbinar_kbr.model.Player
import com.com.dagger.projecbinar_kbr.util.SuitPrefs

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private lateinit var databaseSuitDb: SuitDb
    private lateinit var suitPrefs: SuitPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseSuitDb= SuitDb.getInstance(this)
        suitPrefs= SuitPrefs(this)

        binding.buttonLogoutHomepage.setOnClickListener {
            suitPrefs.clearSharePref()
            toLogin()
            finish()
        }





        var nama=binding.textNamaHomepage.text.toString().trim()

        //tombol profile pic
        binding.imgProfileHomepage.setOnClickListener {
            val drawer=binding.drawerLayoutHomepage
            drawer.openDrawer(Gravity.LEFT)
        }

        //tombol edit nama
        binding.imgEditnamaHomepage.setOnClickListener {
            showEditDialog(nama)
        }

        //tombol logout
        binding.buttonLogoutHomepage.setOnClickListener {
            toLogin()
        }

        //tombol multiplayer
        binding.buttonMultiplayerHomepage.setOnClickListener {
//            toMultiplayerGame()
        }

        //tombol leaderboard
        binding.buttonLeaderboardHomepage.setOnClickListener {
//            toLeaderboard()
        }

        //tombol tutorial
        binding.buttonTutorialHomepage.setOnClickListener {
            toTutorial()
        }

        //tombol setting
        binding.buttonSettingHomepage.setOnClickListener {
//            toSettings()
        }

    }

    private fun showEditDialog(nama:String){
        val builder =AlertDialog.Builder(this)
        val view = DialogEtNamaBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val dialog=builder.create()

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

//    private fun toMultiplayerGame(){
//        val intent = Intent(this,MultiplayerGameActivity::class.java)
//        startActivity(intent)
//    }

//    private fun toLeaderboard(){
//        val intent = Intent(this,LeaderboardActivity::class.java)
//        startActivity(intent)
//    }

    private fun toTutorial(){
        val intent = Intent(this,TutorialActivity::class.java)
        startActivity(intent)
    }

//    private fun toSettings(){
//        val intent = Intent(this,SettingsActivity::class.java)
//        startActivity(intent)
//    }

    private fun toLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

//    private fun toProfile(){
//        val intent = Intent(this,ProfileActivity::class.java)
//        startActivity(intent)
//    }

}