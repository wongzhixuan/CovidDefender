package com.example.coviddefender

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    lateinit var et_country_code: AutoCompleteTextView
    lateinit var et_contact_no: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)

        val countrycode = resources.getStringArray(R.array.country_code_item)
        et_country_code = findViewById<AutoCompleteTextView>(R.id.et_country_code)
        // by default, selected item is null
        val checkedItems: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (relations)
        et_country_code.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Country Code")
            builder.setIcon(R.drawable.ic_country_code)
            builder.setSingleChoiceItems(
                countrycode,
                checkedItems[0],
                DialogInterface.OnClickListener { dialogInterface, i ->
                    checkedItems[0] = i
                    et_country_code.setText(countrycode[i])
                    dialogInterface.dismiss()
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            val custom_dialog: AlertDialog = builder.create()
            //            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        // Verify button
        val btn_verify: Button = findViewById<Button>(R.id.btn_verify)
        btn_verify?.setOnClickListener(View.OnClickListener{
            val intent = Intent (this,RegisterActivity2::class.java).apply{

            }
            startActivity(intent)
        })

        //Back button
        val register_back: ImageButton = findViewById<ImageButton>(R.id.register_back)
        register_back?.setOnClickListener(View.OnClickListener{
            finish()
        })

    }
}