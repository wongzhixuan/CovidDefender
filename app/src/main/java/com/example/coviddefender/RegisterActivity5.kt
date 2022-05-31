package com.example.coviddefender

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity5:AppCompatActivity() {

    lateinit var et_password : TextInputEditText
    lateinit var et_confirm_password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_5)

        //next button
        val btn_login: Button = findViewById<Button>(R.id.btn_login)
        btn_login?.setOnClickListener(View.OnClickListener{
            val intent = Intent (this,LoginActivity::class.java).apply{

            }
            startActivity(intent)
        })

        val register_back: ImageButton = findViewById<ImageButton>(R.id.register_back)
        register_back?.setOnClickListener(View.OnClickListener{
            finish()
        })
    }
}




