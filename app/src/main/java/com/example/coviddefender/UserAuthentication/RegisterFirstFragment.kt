package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.Navigation
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class RegisterFirstFragment : Fragment() {
    // initialize variables to hold values
    private val KEY_EMPTY = ""
    var regexPattern = "^(.+)@(\\S+)$"
    var emptyField = "This field cannot be empty"

    //initialize widgets
    lateinit var et_email: TextInputEditText
    lateinit var btn_verify: Button
    lateinit var login_link: TextView

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
        var view: View = inflater.inflate(R.layout.fragment_register_first, container, false)
        FirebaseApp.initializeApp(context!!)

        //set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.getCurrentUser()

        // if user already logged in, skip login
        if (currentUser != null) {
            reload()
        }

        // get text from edit text
        et_email = view.findViewById(R.id.et_email)

        //verify button
        btn_verify = view.findViewById(R.id.btn_verify)
        btn_verify.setOnClickListener {
            if (validateInputs()) {
                val email: String = et_email.getText().toString().trim()
                var bundle: Bundle = Bundle()
                bundle.putString("email", email)
                findNavController().navigate(R.id.register2, bundle)
            }
        }

        // navigate to login
        login_link = view.findViewById(com.example.coviddefender.R.id.login_link)
        login_link.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        })
        return view
    }

    // check user inputs
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
        return true
    }

    private fun reload() {
        if (currentUser != null) {
            val intent = Intent(context, Navigation::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFirstFragment()
    }
}