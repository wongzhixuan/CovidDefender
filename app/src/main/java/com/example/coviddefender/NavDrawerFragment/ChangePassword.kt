package com.example.coviddefender.NavDrawerFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangePassword : Fragment() {

    lateinit var btn_save : Button
    lateinit var et_password : TextInputEditText
    lateinit var et_new_password : TextInputEditText
    lateinit var et_confirm_new_password : TextInputEditText
    lateinit var old_pwd : String
    lateinit var new_pwd : String
    lateinit var repeat_pwd : String
    lateinit var txt_new:TextInputLayout
    lateinit var txt_old:TextInputLayout
    lateinit var txt_repeat:TextInputLayout


    // Firebase Authentication
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_change_password, container, false)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.getCurrentUser()!!
        et_password = view.findViewById(R.id.et_password)
        et_new_password = view.findViewById(R.id.et_new_password)
        et_confirm_new_password = view.findViewById(R.id.et_confirm_new_password)
        txt_new = view.findViewById(R.id.txt_field_new_password)
        txt_old = view.findViewById(R.id.txt_field_password)
        txt_repeat = view.findViewById(R.id.txt_field_confirm_new_password)

        btn_save = view.findViewById(R.id.btn_save)
        btn_save.setOnClickListener(View.OnClickListener {
            old_pwd = et_password.getText().toString()
            new_pwd = et_new_password.getText().toString()
            repeat_pwd = et_confirm_new_password.getText().toString()
            if (validateInputs()!!) {
                val credential = EmailAuthProvider.getCredential(currentUser!!.getEmail()!!, old_pwd)
                // Prompt user to reprovide sign-in credential
                currentUser!!.reauthenticate(credential)
                    .addOnFailureListener(OnFailureListener { e ->
                        Toast.makeText(context, "Invalid Old Password", Toast.LENGTH_SHORT).show()
                        Log.d("ChangePasswordRe_authentication", "Error: " + e.message)
                    })
                    .addOnSuccessListener(OnSuccessListener<Void?> {
                        currentUser!!.updatePassword(new_pwd)
                            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Password Reset Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Log.d(
                                        "ChangePasswordProcess",
                                        "Error: " + task.exception!!.message
                                    )
                                }
                            })
                    })
            }
        })


        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back )
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_chgpassword_to_editprofile)

        })
        return view
    }
    companion object {

        @JvmStatic
        fun newInstance() = ChangePassword()
    }

    private fun validateInputs(): Boolean? {
        txt_old.setError(null)
        txt_new.setError(null)
        txt_repeat.setError(null)
        val KEY_EMPTY = ""
        val emptyField = "This field cannot be empty"
        // password is empty
        if (old_pwd == KEY_EMPTY) {
            txt_old.setError(emptyField)
            txt_old.requestFocus()
            return false
        }
        // password too short
        if (old_pwd.length < 6) {
            txt_old.setError("Password must be >= 6 characters")
            txt_old.requestFocus()
            return false
        }
        // password is empty
        if (new_pwd == KEY_EMPTY) {
            txt_new.setError(emptyField)
            txt_new.requestFocus()
            return false
        }
        // password too short
        if (new_pwd.length < 6) {
            txt_new.setError("Password must be >= 6 characters")
            txt_new.requestFocus()
            return false
        }
        // password is empty
        if (repeat_pwd == KEY_EMPTY) {
            txt_repeat.setError(emptyField)
            txt_repeat.requestFocus()
            return false
        }
        // password too short
        if (repeat_pwd.length < 6) {
            txt_repeat.setError("Password must be >= 6 characters")
            txt_repeat.requestFocus()
            return false
        }

        // If password and repeat password is not match
        if (repeat_pwd == new_pwd == false) {
            txt_repeat.setError("Password and Confirm Password does not match")
            txt_repeat.requestFocus()
            return false
        }
        return true
    }
}