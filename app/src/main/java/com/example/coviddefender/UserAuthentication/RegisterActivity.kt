package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private val KEY_EMPTY =""
    var regexPattern = "^(.+)@(\\S+)$"
    var emptyField = "This field cannot be empty"

    //initialize widgets
    lateinit var et_email: TextInputEditText
    lateinit var btn_verify: Button
    lateinit var btn_terms_agree: MaterialCheckBox
    lateinit var login_link: TextView

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)

        //set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.getCurrentUser()

        // if user already logged in, skip login
        if(currentUser != null){
            reload()
        }

        et_email = findViewById(R.id.et_email)
       // btn_terms_agree = findViewById(R.id.btn_terms_agree)
        //verify button
        btn_verify = findViewById(R.id.btn_verify)
        btn_verify.setOnClickListener {
            if (validateInputs()){
                val email : String = et_email.getText().toString().trim()
                val intent = Intent(this,RegisterActivity2::class.java).apply {
                    putExtra("email", email)
                }
                startActivity(intent)
            }
        }

        login_link = findViewById(com.example.coviddefender.R.id.login_link)
        login_link.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
    }

    private fun validateInputs(): Boolean {
        et_email.setError(null)
        if (et_email.getText().toString().trim { it <= ' ' } == KEY_EMPTY) {
            et_email.setError(emptyField)
            et_email.requestFocus()
            return false
        }
        if (!Pattern.compile(regexPattern).matcher(et_email.getText().toString().trim { it <= ' ' })
                .matches()
        ) {
            et_email.setError("Invalid email format")
            et_email.requestFocus()
            return false
        }
        /*if (!btn_terms_agree.isChecked()) {
            Toast.makeText(
                applicationContext,
                "Please check the terms and condition",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }*/
        return true
    }

    private fun reload(){
        if(currentUser != null){
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
    }


}