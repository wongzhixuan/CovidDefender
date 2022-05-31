package com.example.coviddefender

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
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity3:AppCompatActivity() {

    lateinit var et_address : TextInputEditText
    lateinit var et_address_2 : AutoCompleteTextView
    lateinit var et_postcode : TextInputEditText
    lateinit var et_state : AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_3)

        // set up state
        val states = resources.getStringArray(R.array.state_items)
        et_state = findViewById<AutoCompleteTextView>(R.id.et_state)
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
        btn_next?.setOnClickListener(View.OnClickListener{
            val intent = Intent (this,RegisterActivity4::class.java).apply{

            }
            startActivity(intent)
        })

        val register_back: ImageButton = findViewById<ImageButton>(R.id.register_back)
        register_back?.setOnClickListener(View.OnClickListener{
            finish()
        })
    }
}




