package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity5:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_5)

        //next button
        val btn_login: Button = findViewById(R.id.btn_login)
        btn_login.setOnClickListener(View.OnClickListener{
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        })

        val register_back: ImageButton = findViewById(R.id.register_back)
        register_back.setOnClickListener(View.OnClickListener{
            finish()
        })
    }

}




