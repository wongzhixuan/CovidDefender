package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    //edittext or dropdown
    lateinit var register_link: TextView
    lateinit var et_login_email: TextInputEditText
    lateinit var btn_login: Button
    lateinit var fgtpwd_link: TextView
    lateinit var et_login_password: TextInputEditText
    lateinit var txt_password: TextInputLayout
    lateinit var txt_email: TextInputLayout

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    // Firestore
    val db = Firebase.firestore

    // variables to hold values
    var regexPattern = "^(.+)@(\\S+)$"
    var emptyField = "This field cannot be empty"
    private val KEY_EMPTY = ""

    lateinit var email: String
    lateinit var password: String
    var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this)

        //set up firebase auth
        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser

        // if user already logged in, skip login
        if (currentUser != null) {
            reload()
        }

        // link widgets
        et_login_email = findViewById(R.id.et_login_email)
        btn_login = findViewById(R.id.btn_login)
        txt_password = findViewById(R.id.txt_field_login_password)
        txt_email = findViewById(R.id.txt_field_login_email)
        et_login_password = findViewById(R.id.et_login_password)

        // btn login on click
        btn_login.setOnClickListener(View.OnClickListener {
            RunLoginTask()
        })

        // navigate to register activity
        register_link = findViewById(R.id.register_link)
        register_link.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterBaseActivity::class.java)
            startActivity(intent)
        })

        // navigate to forget password
        fgtpwd_link = findViewById(R.id.fgtpwd_link)
        fgtpwd_link.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun RunLoginTask() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute() {
            // do something in background
            email = et_login_email.text.toString()
            password = et_login_password.text.toString()

            if (validateInputs()) {
                // sign in user with email and password
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    // if user successfully login
                    currentUser = it.user
                    userId = it.user?.uid

                    // get users data
                    var docRef = db.collection("users").document(userId!!)
                    docRef.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document.exists()) {
                                // store data into sharedPreferences
                                var sharedPreferences: SharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
                                // create editor to edit file
                                var editor: SharedPreferences.Editor = sharedPreferences.edit()
                                // store key and value
                                editor.putString("nric", document.get("nric").toString())
                                // commit
                                editor.commit()

                                Toast.makeText(
                                    this,
                                    "Sign In Sucessful",
                                    Toast.LENGTH_LONG
                                ).show()

                                // navigate to navigation activity
                                startActivity(Intent(this, Navigation::class.java))
                            } else {
                                Toast.makeText(
                                    this,
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

        }

        handler.post() {
            // do something in UI

        }
    }


    // check user inputs
    private fun validateInputs(): Boolean {
        txt_email.error = null
        txt_password.error = null
        if (email == KEY_EMPTY) {
            txt_email.error = emptyField
            txt_email.requestFocus()
            return false
        }
        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            txt_email.error = "Invalid email format"
            txt_email.requestFocus()
            return false
        }
        if (password == KEY_EMPTY) {
            txt_password.error = emptyField
            txt_password.requestFocus()
            return false
        }
        if (password.length < 6) {
            txt_password.error = "Password must be >= 6 characters"
            txt_password.requestFocus()
            return false
        }
        return true
    }

    private fun reload() {
        if (currentUser != null) {
            val intent = Intent(this, Navigation::class.java)
            startActivity(intent)
            finish()
        }
    }

}