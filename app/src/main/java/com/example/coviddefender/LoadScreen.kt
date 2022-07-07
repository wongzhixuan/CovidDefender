package com.example.coviddefender

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.UserAuthentication.LoginActivity

class LoadScreen : AppCompatActivity() {
    var loading_time = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)

        // Splash Screen
        Handler().postDelayed({ // transition from one activity to another
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, loading_time.toLong())

    }
}