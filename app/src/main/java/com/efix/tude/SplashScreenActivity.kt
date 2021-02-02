package com.efix.tude

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        ivSplash.alpha = 0f
        ivSplash.animate().setDuration(2000).alpha(1f).withEndAction {
            val i =Intent(this, LoginActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

    }
}