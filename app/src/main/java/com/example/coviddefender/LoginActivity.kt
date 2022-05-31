package com.example.coviddefender

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class LoginActivity :AppCompatActivity() {
    //edittext or dropdown
    lateinit var et_country_code: AutoCompleteTextView
    lateinit var et_contact_no: TextInputEditText

    //text input layout
    lateinit var txt_field_country_code: TextInputLayout
    lateinit var txt_field_contact_no: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //get reference to all views
        et_contact_no = findViewById(R.id.et_contact_no)
        et_country_code = findViewById(R.id.et_country_code)
        txt_field_contact_no = findViewById(R.id.txt_field_contact_no)
        txt_field_country_code = findViewById(R.id.txt_field_country_code)

        //set up country code dropdown
        val countryCode = resources.getStringArray(R.array.country_code_item)
        et_country_code = findViewById<AutoCompleteTextView>(R.id.et_country_code)
        // by default, selected item is null
        val checkedItems :Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (Country Code)
        et_country_code.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Country Code")
            builder.setIcon(R.drawable.ic_country_code)
            builder.setSingleChoiceItems(countryCode, checkedItems[0], DialogInterface.OnClickListener { dialogInterface, i ->
                checkedItems[0] = i
                et_country_code.setText(countryCode[i])
                dialogInterface.dismiss()
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->  })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        val btn_login: Button = findViewById<Button>(R.id.btn_login)
        btn_login?.setOnClickListener(View.OnClickListener{
            setContentView(R.layout.activity_navigation)
        })

        val login_back: ImageButton = findViewById<ImageButton>(R.id.login_back)
        login_back?.setOnClickListener(View.OnClickListener{
            setContentView(R.layout.activity_register_1)
        })
    }

}