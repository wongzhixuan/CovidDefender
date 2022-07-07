package com.example.coviddefender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coviddefender.UserAuthentication.LoginActivity
import com.example.coviddefender.UserAuthentication.RegisterActivity

class LoadScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)

        //Test code
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}