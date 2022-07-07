package com.example.coviddefender.UserAuthentication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity3 : AppCompatActivity() {

    lateinit var et_address: TextInputEditText
    lateinit var et_postcode: TextInputEditText
    lateinit var et_state: AutoCompleteTextView
    lateinit var register_back: ImageButton

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_3)

        // get values passed from previous activity
        val email = intent.getStringExtra("email")
        val name = intent.getStringExtra("name")
        val nric = intent.getStringExtra("nric")
        val gender = intent.getStringExtra("gender")
        val age = intent.getStringExtra("age")
        val nationality = intent.getStringExtra("nationality")

        // get edit text data
        et_address = findViewById(R.id.et_address)
        et_postcode = findViewById(R.id.et_postcode)
        et_state = findViewById(R.id.et_state)
        register_back = findViewById(R.id.register_back_3)

        // set up state
        val states = resources.getStringArray(R.array.state_items)
        val checkedItems_state: Array<Int> = arrayOf(-1)
        et_state.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("State")
            builder.setIcon(R.drawable.ic_location_small)
            builder.setSingleChoiceItems(
                states,
                checkedItems_state[0],
                DialogInterface.OnClickListener { dialogInterface, i ->
                    checkedItems_state[0] = i
                    et_state.setText(states[i])
                    intent.putExtra("state", states[i])
                    dialogInterface.dismiss()
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        //next button
        val btn_next: Button = findViewById<Button>(R.id.btn_next)
        btn_next.setOnClickListener(View.OnClickListener {

            val add: String = et_address.getText().toString()
            val postcode: String = et_postcode.getText().toString()
            val state: String = et_state.getText().toString()

            if (add.equals("") || postcode.equals("") || state.equals("")) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else {
                // navigate to next activity and pass data
                startActivity(Intent(this, RegisterActivity4::class.java).apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("gender", gender)
                    putExtra("nric", nric)
                    putExtra("address", add)
                    putExtra("postcode", postcode)
                    putExtra("age", age)
                    putExtra("nationality", nationality)
                    putExtra("state", state)
                })
            }
        })

        //Back Button
        register_back.setOnClickListener(View.OnClickListener {
            finish()
        })
    }


    override fun onResume() {
        super.onResume()
    }
}




