package com.example.coviddefender

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    lateinit var et_contact_no: TextInputEditText
    lateinit var btn_verify: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)


        val login_link : TextView = findViewById(R.id.login_link)
        login_link.setOnClickListener(View.OnClickListener {
            val intent = Intent (this,LoginActivity::class.java).apply{

            }
            startActivity(intent)
        })

        // Verify button
        btn_verify = findViewById<Button>(R.id.btn_verify)
        btn_verify?.setOnClickListener(View.OnClickListener{
            val intent = Intent (this,RegisterActivity2::class.java).apply{

            }
            startActivity(intent)
        })

    }

    override fun onResume() {
        super.onResume()
    }
}