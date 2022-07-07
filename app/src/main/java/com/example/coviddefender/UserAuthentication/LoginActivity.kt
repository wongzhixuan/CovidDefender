package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class LoginActivity :AppCompatActivity() {
    //edittext or dropdown
    lateinit var register_link : TextView
    lateinit var et_login_email : TextInputEditText
    lateinit var btn_login : Button
    lateinit var fgtpwd_link : TextView
    lateinit var et_login_password : TextInputEditText
    lateinit var txt_password : TextInputLayout
    lateinit var txt_email : TextInputLayout

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    // Firestore
    private val firestore: FirebaseFirestore? = null

    var regexPattern = "^(.+)@(\\S+)$"
    var emptyField = "This field cannot be empty"
    private val KEY_EMPTY = ""

    lateinit var email:String
    lateinit var password:String
    var userId:String? = null

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser

        et_login_email = findViewById(R.id.et_login_email)
        btn_login = findViewById(R.id.btn_login)
        txt_password = findViewById(R.id.txt_field_login_password)
        txt_email = findViewById(R.id.txt_field_login_email)
        et_login_password = findViewById(R.id.et_login_password)

        btn_login.setOnClickListener(View.OnClickListener {
            email = et_login_email.getText().toString()
            password = et_login_password.getText().toString()

            if (validateInputs()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    currentUser = it.user
                    userId = it.user?.uid
                    var docRef = db.collection("users").document(userId!!)
                    docRef.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document.exists()) {
                                Toast.makeText(this,
                                    "Sign In Sucessful",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(Intent(this, Navigation::class.java))
                            }else {
                                Toast.makeText(this,
                                    "Incorrect email/password or user does not exists. Please try again.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Login Failed! Invalid account!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


        register_link = findViewById(R.id.register_link)
        register_link.setOnClickListener(View.OnClickListener {
            val intent = Intent (this, RegisterActivity::class.java)
            startActivity(intent)
        })

        val login_back: ImageButton = findViewById<ImageButton>(R.id.login_back)
        login_back.setOnClickListener(View.OnClickListener{
            val intent = Intent (this, RegisterActivity::class.java)
            startActivity(intent)
        })

        fgtpwd_link = findViewById(R.id.fgtpwd_link)
        fgtpwd_link.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(): Boolean {
        txt_email.setError(null)
        txt_password.setError(null)
        if (email == KEY_EMPTY) {
            txt_email.setError(emptyField)
            txt_email.requestFocus()
            return false
        }
        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            txt_email.setError("Invalid email format")
            txt_email.requestFocus()
            return false
        }
        if (password == KEY_EMPTY) {
            txt_password.setError(emptyField)
            txt_password.requestFocus()
            return false
        }
        if (password!!.length < 6) {
            txt_password.setError("Password must be >= 6 characters")
            txt_password.requestFocus()
            return false
        }
        return true
    }


    override fun onResume() {
        super.onResume()
    }
}