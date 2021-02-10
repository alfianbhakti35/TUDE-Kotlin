package com.efix.tude.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.guide.StatusDaftarGuideActivity
import com.efix.tude.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register_guide.*
import java.util.*

class RegisterGuideActivity : AppCompatActivity() {

    var selecPhotoProflie : Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_guide)

//        Fungsi Tombol Pilih Img
        imageViewPilihPhotoGuide.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 2)
        }

//        Fungsi Tombol daftar Guide
        buttonDaftarGuide.setOnClickListener {
            registerGuideByEmaildanPassword()
        }

    }

    private fun registerGuideByEmaildanPassword(){
        val email = editTextEmailGuideRegister.text.toString()
        val password = editTextPasswordGuideRegister.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                uploadFilePhoto()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }

//    Fungsi Untuk Upload File Img Ke server
    private fun uploadFilePhoto(){
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("img/guide/$fileName")
        ref.putFile(selecPhotoProflie!!)
            .addOnSuccessListener {
                ref.downloadUrl
                    .addOnSuccessListener {
                        simpanDataUser(it.toString())
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

//    Fungsi untuk Simpan data user ke database
    private fun simpanDataUser(urlImgGuide : String){
//        get Nilai Dari Input
        val namaLengkap = editTextNamaLngkapGuideRegister.text.toString()
        val email = editTextEmailGuideRegister.text.toString()
        val noHp = editTextNoHpGuideRegister.text.toString()
        val alamat = editTextAlamatGuideRegister.text.toString()
        val password = editTextPasswordGuideRegister.text.toString()

//        Inisalisasi Firebase
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        val userGuide = User(uid, namaLengkap, email, noHp, alamat, password, "guide", urlImgGuide, "diprosess", "Level 1")

        ref.setValue(userGuide)
            .addOnSuccessListener {
                val i = Intent(this, StatusDaftarGuideActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3 && resultCode == Activity.RESULT_OK && data != null){
            selecPhotoProflie = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selecPhotoProflie)
            imageViewPilihPhotoGuide.setImageBitmap(bitmap)
        }
    }
}