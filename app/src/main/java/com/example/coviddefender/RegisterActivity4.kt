package com.example.coviddefender

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity4:AppCompatActivity() {

    lateinit var et_password : TextInputEditText
    lateinit var et_confirm_password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_4)

        //next button
        val btn_next: Button = findViewById<Button>(R.id.btn_next)
        btn_next?.setOnClickListener(View.OnClickListener{
            setContentView(R.layout.activity_register_5)
        })

        val register_back: ImageButton = findViewById<ImageButton>(R.id.register_back)
        register_back?.setOnClickListener(View.OnClickListener{
            setContentView(R.layout.activity_register_3)
        })
    }
}




