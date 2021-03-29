package com.project_binar.kbg.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.project_binar.kbg.R
import com.project_binar.kbg.api.ApiClient
import com.project_binar.kbg.data.db.SuitDb
import com.project_binar.kbg.databinding.ActivityProfileBinding
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.repository.RemoteRepository
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.util.SuitPrefs
import com.project_binar.kbg.util.SuitViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var selectedImage: Uri

    //    private lateinit var presenter: ProfilPresenterImp
    private lateinit var viewModel: ProfileViewModel

    companion object {
        const val DATA_PLAYER = "data_player"
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val REQUEST_CODE_IMAGE_CAMERA = 200
        private const val REQUEST_PERMISSION = 1
        const val REQ_IMG_PROFILE = 12
    }

    private var _fileImg: File? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = RemoteRepository(ApiClient.service())
        val SuitViewModelFactory = SuitViewModelFactory(repository)
        val suitPrefs = SuitPrefs(this)
        viewModel = ViewModelProvider(this, SuitViewModelFactory).get(ProfileViewModel::class.java)
        viewModel.getProfile(suitPrefs.token!!)
        binding.playerProfile.visibility = View.GONE
        binding.statsLayout.visibility = View.GONE
        viewModel.getDataProfile.observe(this, {
            binding.etEditNameProfile.setText(it.username)
            binding.etEditEmailProfile.setText(it.email)
            Glide.with(this).load(it.photo).placeholder(R.drawable.img_profile_picture).circleCrop()
                .fitCenter().into(binding.imageProfilePic)
            binding.progressBar.visibility = View.GONE
            binding.playerProfile.visibility = View.VISIBLE
            binding.statsLayout.visibility = View.VISIBLE
        })
        viewModel.getError.observe(this, {
            toHome()
        })

        val dataPlayer = intent.getParcelableExtra<Player>(DATA_PLAYER)
//        binding.etEditNameProfile.setText(dataPlayer?.nama)

        dataPlayer?.apply {
            win?.let { binding.win.text = it.toString() }
            lose?.let { binding.lose.text = it.toString() }
            rate?.let { binding.winrate.text = "${it.toInt()}%" }
        }
        val playerDb = SuitDb.getInstance(this)
        binding.btnEditImgProfile.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)
                .saveDir(this.getExternalFilesDir(null)!!)
                .maxResultSize(512, 512)
                .start(REQ_IMG_PROFILE)
        }
        binding.btnBackProfile.setOnClickListener {
            toHome()
        }
        /* binding.imageProfilePic.setOnClickListener {
             selectImage(this)
         }*/

        /*edit profil*/
        binding.btnSaveNameProfil.setOnClickListener {
            val profilreReq: HashMap<String, RequestBody> = HashMap()
            profilreReq["username"] = binding.etEditNameProfile.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            profilreReq["email"] = binding.etEditEmailProfile.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val requestImg = _fileImg?.asRequestBody("*/*".toMediaTypeOrNull())
            val imgPart = requestImg?.let { it1 ->
                MultipartBody.Part.createFormData("photo", _fileImg!!.name, it1)
            }
            imgPart?.let { it1 -> viewModel.updProfile(suitPrefs.token, profilreReq, it1) }

        }

//        binding.btnSaveNameProfil.setOnClickListener {
//            presenter = ProfilPresenterImp(this, playerDb.playerDao())
//            dataPlayer?.id?.let {
//                presenter.updateNamePlayer(binding.etEditNameProfile.text.toString(), it)
//            }
//        }
    }

    //    override fun showUpdatePlayer() {
//        runOnUiThread {
//            Toast.makeText(this, "Berhasil Update Nama Player", Toast.LENGTH_SHORT).show()
//            Handler(Looper.getMainLooper()).postDelayed({
//                finish()
//            }, 1000)
//        }
//
//    }
    private fun toHome() {
        val intentProfile = Intent(this, HomeActivity::class.java)
        intentProfile.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentProfile)
        finish()
    }

    private fun startCamera() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePicture, REQUEST_CODE_IMAGE_CAMERA)
    }

    private fun openImageLibrary() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        pickPhoto.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE_PICKER)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        startCamera()
                    } else {
                        requestPermissions(
                            arrayOf(android.Manifest.permission.CAMERA),
                            REQUEST_PERMISSION
                        )
                    }
                }
                options[item] == "Choose from Gallery" -> {
                    openImageLibrary()
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            _fileImg = ImagePicker.getFile(data)!!
            if (requestCode == REQ_IMG_PROFILE) {
                Glide.with(this).load(_fileImg)
                    .placeholder(R.drawable.img_profile_picture).fitCenter()
                    .into(binding.imageProfilePic)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            }
        }
    }

}