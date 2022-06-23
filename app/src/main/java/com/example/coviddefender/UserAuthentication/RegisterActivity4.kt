package com.example.coviddefender.UserAuthentication

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class RegisterActivity4:AppCompatActivity() {

    lateinit var et_password : TextInputEditText
    lateinit var et_confirm_password: TextInputEditText

    val db = Firebase.firestore

    private val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_4)

        var id = intent.getStringExtra("DOCUMENT_ID")
        et_password = findViewById(R.id.et_password)
        et_confirm_password = findViewById(R.id.et_confirm_password)
        val pass : String = et_password.getText().toString()
        val cpass : String = et_confirm_password.getText().toString()

        //next button
        val btn_next: Button = findViewById<Button>(R.id.btn_next)
        btn_next.setOnClickListener(View.OnClickListener{
            val pass : String = et_password.getText().toString()
            val cpass : String = et_confirm_password.getText().toString()
            if (pass.equals("")||cpass.equals("")) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            }else {
                // check user input a valid formatted password
                if(!pass.matches(passwordPattern.toRegex()) || pass.length < 8) {
                    Toast.makeText(this, "Invalid Password. Minimum length 8, " +
                            "at least 1 uppercase, 1 number, 1 special character", Toast.LENGTH_LONG)
                        .show()

                }else if (cpass != pass){
                    // show error on input invalid password
                        Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG)
                            .show()
                }else {
                    if (id != null) {
                        db.collection("users").document(id).update("password", pass)

                        val intent = Intent(this, RegisterActivity5::class.java).apply {
                            putExtra("DOCUMENT_ID", id)
                        }
                        startActivity(intent)
                    }
                }
            }
        })

        val register_back: ImageButton = findViewById<ImageButton>(R.id.register_back)
        register_back?.setOnClickListener(View.OnClickListener{
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
    }
}




