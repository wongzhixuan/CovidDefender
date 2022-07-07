package com.example.coviddefender.UserAuthentication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity4:AppCompatActivity() {

    lateinit var et_password : TextInputEditText
    lateinit var et_confirm_password : TextInputEditText
    lateinit var register_back: ImageButton
    lateinit var txt_password : TextInputLayout
    lateinit var txt_confirm_password : TextInputLayout
    lateinit var btn_next : Button
    
    private val KEY_EMPTY =""
    var emptyField = "This field cannot be empty"

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_4)

        //set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.getCurrentUser()

        // if user already logged in, skip login
        if (currentUser != null) {
            reload()
        }

        et_password = findViewById(R.id.et_password)
        et_confirm_password = findViewById(R.id.et_confirm_password)
        register_back = findViewById(R.id.register_back_4)
        txt_password = findViewById(R.id.txt_field_password)
        txt_confirm_password = findViewById(R.id.txt_field_confirm_password)
        btn_next = findViewById(R.id.btn_next)

        //next button
        btn_next.setOnClickListener(View.OnClickListener{
            val password : String = et_password.getText().toString()
            val email = intent.getStringExtra("email").toString()
            val name = intent.getStringExtra("name").toString()
            val nric = intent.getStringExtra("nric").toString()
            val gender = intent.getStringExtra("gender").toString()
            val age = intent.getStringExtra("calAge").toString()
            val nationality = intent.getStringExtra("nationality").toString()
            val address = intent.getStringExtra("address").toString()
            val postcode = intent.getStringExtra("postcode").toString()
            val state = intent.getStringExtra("state").toString()

            if (validateInputs()) {
                // create user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                        object : OnSuccessListener<AuthResult?> {
                            override fun onSuccess(authResult: AuthResult?) {
                                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show()
                                currentUser = mAuth.getCurrentUser()
                                val userId : String = currentUser!!.getUid()
                                val profileChangeRequest = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(null)
                                    .build()
                                currentUser!!.updateProfile(profileChangeRequest)
                                    .addOnSuccessListener(OnSuccessListener<Void?> {
                                        Log.d(
                                            TAG,
                                            "Profile updated: " + currentUser!!.getDisplayName()
                                        )
                                    })

                                // create new document for new user
                                val documentReference: DocumentReference =
                                    db.collection("users").document(userId)
                                val userDetails: MutableMap<String, Any> = HashMap()
                                userDetails["fullName"] = name
                                userDetails["nric"] = nric
                                userDetails["nationality"] = nationality
                                userDetails["emailAdd"] = email
                                userDetails["gender"] = gender
                                userDetails["age"] = age
                                userDetails["address"] = address
                                userDetails["postcode"] = postcode
                                userDetails["state"] = state
                                userDetails["password"] = password
                                documentReference.set(userDetails).addOnSuccessListener {
                                    Log.d(
                                        TAG,
                                        "on Success: user profile created for $userId"
                                    )
                                    startActivity(Intent(applicationContext,RegisterActivity5::class.java))
                                }.addOnFailureListener { e -> Log.d(TAG, "on Failure: " + e.message) }
                                reload()
                            }

                        }).addOnFailureListener { e ->
                        Toast.makeText(
                            getApplicationContext(),
                            "Sign Up Failed! Please try again!" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }

        })

        //Back Button
        register_back.setOnClickListener(View.OnClickListener{
            finish()
        })
    }

    private fun validateInputs(): Boolean {
        txt_password.setError(null)
        txt_confirm_password.setError(null)
        // password is empty
        val password : String = et_password.getText().toString()
        val confirm_password : String = et_confirm_password.getText().toString()
        if (password == KEY_EMPTY) {
            txt_password.setError(emptyField)
            txt_password.requestFocus()
            return false
        }
        // password too short
        if (password.length < 6) {
            txt_password.setError("Password must be >= 6 characters")
            txt_password.requestFocus()
            return false
        }
        // If repeat password is empty
        if (confirm_password == KEY_EMPTY) {
            txt_confirm_password.setError(emptyField)
            txt_confirm_password.requestFocus()
            return false
        }

        // If password and repeat password is not match
        if (confirm_password == password == false) {
            txt_confirm_password.setError("Password and Confirm Password does not match")
            txt_confirm_password.requestFocus()
            return false
        }
        return true
    }

    private fun reload(){
        if(currentUser != null){
            val intent = Intent(this,Navigation::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
    }



}




