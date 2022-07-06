package com.example.coviddefender.UserAuthentication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*



class RegisterActivity2 : AppCompatActivity() {

    lateinit var et_country_code: AutoCompleteTextView
    lateinit var et_full_name: TextInputEditText
    lateinit var et_NRIC: TextInputEditText
    lateinit var et_age: TextInputEditText
    lateinit var radio_group_gender : RadioGroup
    lateinit var radioButton:RadioButton
    lateinit var btn_next : Button

    val db = Firebase.firestore
    var calAge : Int = 0
    var country : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_2)

        var email = intent.getStringExtra("email")


        et_full_name = findViewById(R.id.et_full_name)
        et_NRIC = findViewById(R.id.et_NRIC)
        et_age = findViewById(R.id.et_age)
        btn_next = findViewById(R.id.btn_next)
        radio_group_gender = findViewById(R.id.radio_group_gender)
        et_country_code = findViewById(R.id.et_country_code)

        // Nationality
        val countrycode = resources.getStringArray(R.array.country_code_item)
        val checkedItems: Array<Int> = arrayOf(-1) // by default, selected item is null
        et_country_code.setOnClickListener(View.OnClickListener{
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Nationality")
            builder.setIcon(R.drawable.ic_country_code)
            builder.setSingleChoiceItems(
                countrycode,
                checkedItems[0],
                DialogInterface.OnClickListener { dialogInterface, i ->
                    checkedItems[0] = i
                    et_country_code.setText(countrycode[i])
                    country = countrycode[i]
                    dialogInterface.dismiss()
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            val custom_dialog: AlertDialog = builder.create()
            //            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        // Birth Date validation
        et_age.setInputType(InputType.TYPE_NULL)
        et_age.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            val picker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    et_age.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)

                    var dob = Calendar.getInstance()
                    dob.set(year, month, day)

                    var age = cldr.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
                    if(cldr.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
                    {
                        age--
                    }
                    calAge = age
                }
                , year
                , month
                , day)
            picker.show()
        }

        // Next button
        val btn_next: Button = findViewById<Button>(R.id.btn_next)
        btn_next.setOnClickListener(View.OnClickListener{

            val fullname : String = et_full_name.getText().toString()
            val nric : String = et_NRIC.getText().toString()
            var birthdate : String = et_age.getText().toString()
            val nationality : String = et_country_code.getText().toString()

            if (fullname.equals("")||nric.equals("")||nationality.equals("")
                ||radio_group_gender.getCheckedRadioButtonId() == -1 || birthdate.equals("")) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else{

                if(radio_group_gender==null) {
                    Toast.makeText(this, "Please select your gender", Toast.LENGTH_LONG)
                        .show()
                }
                else {
                    // Gender Validation
                    val selectedId: Int = radio_group_gender.getCheckedRadioButtonId()
                    radioButton = findViewById<View>(selectedId) as RadioButton
                    val gender: String = radioButton.text.toString()

                    val intent = Intent(this,RegisterActivity3::class.java).apply {
                        putExtra("email",email)
                        putExtra("name",fullname)
                        putExtra("gender",gender)
                        putExtra("nric",nric)
                        putExtra("age",calAge.toString())
                        putExtra("nationality",country)
                    }
                    startActivity(intent)
                }

            }
        })

        //Back Button
        val register_back: ImageButton = findViewById<ImageButton>(R.id.register_back)
        register_back.setOnClickListener(View.OnClickListener{
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
    }
}