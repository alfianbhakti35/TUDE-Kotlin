package com.efix.tude

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.efix.tude.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnToLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        btnRegister.setOnClickListener {
            register()
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
            Toast.makeText(this, "Semua data harus diisi !", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("Register", "email = $email")
        Log.d("Register", "password = $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    simpanDataUser()
                    Log.d("Mains", "REGISTER BERHASIL = ${it.result?.user?.uid}")
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
    }

    private fun simpanDataUser(){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val role = "user"
        val user = User(uid, etNameRegister.text.toString(), etEmailRegister.text.toString(), etNoHpRegister.text.toString(), etAlamatRegister.text.toString(), etPasswordRegister.text.toString(), role)

        ref.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
//                val i = Intent(this, UserMenuActivity::class.java)
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                startActivity(i)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
            }
    }
}

