package com.nglb.filmworld.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nglb.filmworld.R
import com.nglb.filmworld.presentation.ui.home.HomeActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        run {
            // I used the postDelayed(Runnable, time) method
            // to send a message with a delayed time.
            // this activity move to Home activity
            Handler().postDelayed({
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                // this method finish() used to finish the activity
                this@SplashActivity?.finish()
            }, 3000) // 3000 is the delayed time in milliseconds.

        }
    }
}