package com.efix.tude.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.efix.tude.R
import com.efix.tude.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_menu.*
import kotlinx.android.synthetic.main.fragment_profile_user.*

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

//    Tombol Logout

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addGuide -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun rubahFragment(fragment: Fragment){
        if (fragment != null){
            val transisi = supportFragmentManager.beginTransaction()
            transisi.replace(R.id.frameLayoutUser, fragment)
            transisi.commit()
        }
    }
}