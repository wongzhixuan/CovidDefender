package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var btn_back: ImageButton
    lateinit var txt_email: TextInputLayout
    lateinit var et_email: TextInputEditText
    lateinit var btn_send: MaterialButton
    lateinit var progressBar: ProgressBar
    lateinit var email: String
    var regexPattern = "^(.+)@(\\S+)$"
    var emptyField = "This field cannot be empty"

    // Firebase Authentication
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser

        // if user already logged in, skip login
        if (currentUser != null) {
            reload()
        }

        // link widgets
        btn_back = findViewById(R.id.btn_back)
        txt_email = findViewById(R.id.txt_field_email)
        et_email = findViewById(R.id.et_email)
        btn_send = findViewById(R.id.btn_send)
        progressBar = findViewById(R.id.progressBar)

        // initial progress bar
        progressBar.setVisibility(View.INVISIBLE)
        btn_back.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        })
        btn_send.setOnClickListener(View.OnClickListener {
            email = et_email.getText().toString().trim { it <= ' ' }
            if (validateInputs()) {
                progressBar.setVisibility(View.VISIBLE)
                mAuth!!.sendPasswordResetEmail(email!!).addOnSuccessListener {
                    Log.d("ResetPassword", "on Success: Email sent to $email")
                    Toast.makeText(
                        applicationContext,
                        "Email sent! Please check your email! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.setVisibility(View.INVISIBLE)
                }.addOnFailureListener { e ->
                    Log.d(
                        "ResetPassword",
                        "on Failure: " + email + " " + e.message
                    )
                    Toast.makeText(applicationContext, "Invalid Email", Toast.LENGTH_SHORT)
                        .show()
                    progressBar.setVisibility(View.INVISIBLE)
                }
            }
        })
    }

    private fun validateInputs(): Boolean {
        txt_email!!.error = null
        if (email == KEY_EMPTY) {
            txt_email!!.error = emptyField
            txt_email!!.requestFocus()
            return false
        }
        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            txt_email!!.error = "Invalid email format"
            txt_email!!.requestFocus()
            return false
        }
        return true
    }

    private fun reload() {
        if (currentUser != null) {
            val intent = Intent(applicationContext, ForgetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val KEY_EMPTY = ""
    }
}