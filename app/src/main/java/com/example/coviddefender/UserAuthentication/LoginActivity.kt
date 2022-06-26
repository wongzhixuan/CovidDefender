package com.example.coviddefender.UserAuthentication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity :AppCompatActivity() {
    //edittext or dropdown
    lateinit var register_link : TextView
    lateinit var et_login_contact_no : TextInputEditText
    lateinit var btn_login : Button


    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        et_login_contact_no = findViewById(R.id.et_login_contact_no)
        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener(View.OnClickListener {
            var contactno : String = et_login_contact_no.getText().toString()
            if (TextUtils.isEmpty(contactno)) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }else{
                db.collection("users")
                    .whereEqualTo("contactNo", contactno)
                    .get()
                    .addOnSuccessListener { documents ->
                        if(documents.size()!=0) {
                            Toast.makeText(this,
                                "Sign In Sucessful",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(Intent(this, Navigation::class.java))
                        }
                        else {
                            Toast.makeText(this,
                                "Incorrect phone number or user does not exists. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error. Please Try Again ", exception)
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
    }


    override fun onResume() {
        super.onResume()
    }
}