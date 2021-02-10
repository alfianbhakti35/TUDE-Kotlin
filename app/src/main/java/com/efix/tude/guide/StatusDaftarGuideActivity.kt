package com.efix.tude.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.efix.tude.R
import com.efix.tude.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_status_daftar_guide.*

class StatusDaftarGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_daftar_guide)

        buttonSelesai.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        }
    }
}