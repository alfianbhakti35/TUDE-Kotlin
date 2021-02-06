package com.efix.tude.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.efix.tude.MainActivity
import com.efix.tude.R
import com.efix.tude.model.User
import com.efix.tude.user.UserMenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var selecPhotoProflie : Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btnImgUserRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        btnToLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        btnRegister.setOnClickListener {
            progressBarRegister.visibility = View.VISIBLE
            register()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            selecPhotoProflie = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selecPhotoProflie)
            imgPicUserRegister.setImageBitmap(bitmap)
        }
    }

    private fun uploadImg(){
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("users/$fileName")
        ref.putFile(selecPhotoProflie!!)
                .addOnSuccessListener {
                    ref.downloadUrl
                            .addOnSuccessListener {
                                simpanDataUser(it.toString())
                            }
                            .addOnFailureListener {
                                Log.d("TAGS", "GAGAL mendapatkan Url")
                            }
                }
                .addOnFailureListener {
                    Log.d("RegisterUploadImg", "GAGAL UPLOAD")
                }
    }

//    Function untuk register
    private fun register(){
        val name = etNameRegister.text.toString()
        val email = etEmailRegister.text.toString()
        val noHp = etNoHpRegister.text.toString()
        val alamat = etAlamatRegister.text.toString()
        val password = etPasswordRegister.text.toString()

        if(name.isEmpty() or email.isEmpty() or noHp.isEmpty() or alamat.isEmpty() or password.isEmpty()){
            progressBarRegister.visibility = View.GONE
            Toast.makeText(this, "Semua data harus diisi !", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("Register", "email = $email")
        Log.d("Register", "password = $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    uploadImg()
                    Log.d("Mains", "REGISTER BERHASIL = ${it.result?.user?.uid}")
                }
                .addOnFailureListener {
                    progressBarRegister.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
    }

    private fun simpanDataUser(imageUrl : String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val role = "user"
        val user = User(uid, etNameRegister.text.toString(), etEmailRegister.text.toString(), etNoHpRegister.text.toString(), etAlamatRegister.text.toString(), etPasswordRegister.text.toString(), role, imageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                progressBarRegister.visibility = View.GONE
                val i = Intent(this, UserMenuActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(i)
                Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressBarRegister.visibility = View.GONE
                Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
            }
    }
}

