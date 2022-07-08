package com.example.coviddefender.UserAuthentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Register4Fragment : Fragment() {

    lateinit var et_password: TextInputEditText
    lateinit var et_confirm_password: TextInputEditText
    lateinit var register_back: ImageButton
    lateinit var txt_password: TextInputLayout
    lateinit var txt_confirm_password: TextInputLayout
    lateinit var btn_next: Button

    private val KEY_EMPTY = ""
    var emptyField = "This field cannot be empty"

    lateinit var password: String
    lateinit var email: String
    lateinit var name: String
    lateinit var nric: String
    lateinit var gender: String
    lateinit var age: String
    lateinit var nationality: String
    lateinit var address: String
    lateinit var postcode: String
    lateinit var state: String

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register4, container, false)


        //set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.getCurrentUser()

        // get data passed from previous
        email = arguments?.getString("email").toString()
        name = arguments?.getString("name").toString()
        nric = arguments?.getString("nric").toString()
        gender = arguments?.getString("gender").toString()
        age = arguments?.getString("age").toString()
        nationality = arguments?.getString("nationality").toString()
        address = arguments?.getString("address").toString()
        postcode = arguments?.getString("postcode").toString()
        state = arguments?.getString("state").toString()

        // get edit text data
        et_password = view.findViewById(R.id.et_password)
        et_confirm_password = view.findViewById(R.id.et_confirm_password)
        register_back = view.findViewById(R.id.register_back_4)
        txt_password = view.findViewById(R.id.txt_field_password)
        txt_confirm_password = view.findViewById(R.id.txt_field_confirm_password)
        btn_next = view.findViewById(R.id.btn_next)

        btn_next.setOnClickListener {
            registerNewUser()
        }

        //Back Button
        register_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.register1)
        })
        return view
    }

    private fun registerNewUser() {

        // get data from edit text
        val password: String = et_password.getText().toString()

        if (validateInputs()) {
            // create user
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                // set current user
                currentUser = mAuth.getCurrentUser()
                val userId: String = currentUser!!.getUid()
                // set username
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(null)
                    .build()
                currentUser!!.updateProfile(profileChangeRequest)

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
                    mAuth.signOut()
                    findNavController().navigate(R.id.register5)
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Sign Up Failed! Please try again!" + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    // check user input
    private fun validateInputs(): Boolean {
        txt_password.setError(null)
        txt_confirm_password.setError(null)
        // password is empty
        val password: String = et_password.getText().toString()
        val confirm_password: String = et_confirm_password.getText().toString()
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

    private fun getDateFromBundle() {


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Register4Fragment()
    }
}