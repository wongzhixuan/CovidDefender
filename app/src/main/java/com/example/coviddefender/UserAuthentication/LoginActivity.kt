package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity :AppCompatActivity() {
    //edittext or dropdown
    lateinit var et_contact_no: TextInputEditText

    //text input layout
    lateinit var txt_field_country_code: TextInputLayout
    lateinit var txt_field_contact_no: TextInputLayout
    lateinit var register_link : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //get reference to all views
        et_contact_no = findViewById(R.id.et_contact_no)
        txt_field_contact_no = findViewById(R.id.txt_field_contact_no)
        register_link = findViewById(R.id.register_link)


        register_link.setOnClickListener(View.OnClickListener {
            val intent = Intent (this, RegisterActivity::class.java).apply{

            }
            startActivity(intent)
        })

        val btn_login: Button = findViewById<Button>(R.id.btn_login)
        btn_login?.setOnClickListener(View.OnClickListener{
            val intent = Intent (this, Navigation::class.java).apply{

            }
            startActivity(intent)
        })

        val login_back: ImageButton = findViewById<ImageButton>(R.id.login_back)
        login_back?.setOnClickListener(View.OnClickListener{
            val intent = Intent (this, RegisterActivity::class.java).apply{

            }
            startActivity(intent)
        })
    }
    override fun onResume() {
        super.onResume()
    }
}