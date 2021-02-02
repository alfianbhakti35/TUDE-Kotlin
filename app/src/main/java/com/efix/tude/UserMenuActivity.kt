package com.efix.tude

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.efix.tude.user.HomeUserFragment
import com.efix.tude.user.ProfileUserFragment
import com.efix.tude.user.TransaksiUserFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user_menu.*

class UserMenuActivity : AppCompatActivity() {

    private val homeUserdFragment = HomeUserFragment()
    private val transaksiFragment = TransaksiUserFragment()
    private val profileFragment = ProfileUserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        rubahFragment(homeUserdFragment)
        navigasiMenuUsers.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.btnHome -> rubahFragment(homeUserdFragment)
                R.id.btnTransaksi -> rubahFragment(transaksiFragment)
                R.id.btnProfile -> rubahFragment(profileFragment)
            }
            true
        }
    }

    private fun rubahFragment(fragment: Fragment){
        if (fragment != null){
            val transisi = supportFragmentManager.beginTransaction()
            transisi.replace(R.id.frameLayoutUser, fragment)
            transisi.commit()
        }
    }
}