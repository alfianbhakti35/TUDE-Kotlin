package com.efix.tude.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.user.UserMenuActivity
import com.efix.tude.admin.AdminMenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginProgrss.visibility = View.GONE

//        Pindah ke activity register
        btnToRegister.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener {
            loginProgrss.visibility = View.VISIBLE
            login()
        }
    }

    private fun login() {
        val email = etEmailLogin.text.toString()
        val password = etPasswordLogin.text.toString()

        if (email == null || password == null){
            Toast.makeText(this, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = FirebaseAuth.getInstance()
                val uidUser = uid.currentUser?.uid

                Log.d("TAGS", "UID = $uidUser")
                val ref = FirebaseDatabase.getInstance().getReference("users/$uidUser")
                ref.addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        loginProgrss.visibility = View.GONE
                        Log.d("TAGS", error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("TAGS", "DATA = TIDAK DITEMUKAN")
                        var sb = StringBuilder()
                        val coba = snapshot.child("role").getValue()
                        sb.append(coba)
                        Log.d("TAGS", "DATA = $sb")

//                        Cek User role
                        if (sb.toString() == "user"){
                            Log.d("TAGS", "LOGIN KE USER")
                            val intent = Intent(this@LoginActivity, UserMenuActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            loginProgrss.visibility = View.GONE
                            startActivity(intent)
                        }else{
                            Log.d("TAGS", "LOGIN KE ADMIN")
                            val intent = Intent(this@LoginActivity, AdminMenuActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            loginProgrss.visibility = View.GONE
                            startActivity(intent)
                        }
                    }
                })
            }
            .addOnFailureListener {
                loginProgrss.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }

    }
}