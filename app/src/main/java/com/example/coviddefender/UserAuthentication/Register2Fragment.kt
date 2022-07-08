package com.example.coviddefender.UserAuthentication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class Register2Fragment : Fragment() {
    lateinit var et_country_code: AutoCompleteTextView
    lateinit var et_full_name: TextInputEditText
    lateinit var et_NRIC: TextInputEditText
    lateinit var et_age: TextInputEditText
    lateinit var radio_group_gender: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var btn_next: Button

    lateinit var email: String
    val db = Firebase.firestore
    var calAge: Int = 0
    var country: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_register2, container, false)

        // link widgets
        et_full_name = view.findViewById(R.id.et_full_name)
        et_NRIC = view.findViewById(R.id.et_NRIC)
        et_age = view.findViewById(R.id.et_age)
        btn_next = view.findViewById(R.id.btn_next)
        radio_group_gender = view.findViewById(R.id.radio_group_gender)
        et_country_code = view.findViewById(R.id.et_country_code)

        // get data from previoud page
        email = arguments?.getString("email").toString()


        // Nationality
        val countrycode = resources.getStringArray(R.array.country_code_item)
        val checkedItems: Array<Int> = arrayOf(-1) // by default, selected item is null
        et_country_code.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(context)
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
            // custom_dialog.window?.setBackgroundDrawableResource()
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
            val picker = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    et_age.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)

                    var dob = Calendar.getInstance()
                    dob.set(year, month, day)

                    var age = cldr.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
                    if (cldr.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                        age--
                    }
                    calAge = age
                }, year, month, day
            )
            picker.show()
        }

        // Next button
        val btn_next: Button = view.findViewById<Button>(R.id.btn_next)
        btn_next.setOnClickListener(View.OnClickListener {
            val fullname: String = et_full_name.getText().toString()
            val nric: String = et_NRIC.getText().toString()
            var birthdate: String = et_age.getText().toString()
            val nationality: String = et_country_code.getText().toString()

            if (fullname.equals("") || nric.equals("") || nationality.equals("")
                || radio_group_gender.getCheckedRadioButtonId() == -1 || birthdate.equals("")
            ) {
                // Check if all fields are filled
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else {

                if (radio_group_gender == null) {
                    Toast.makeText(context, "Please select your gender", Toast.LENGTH_LONG)
                        .show()
                } else {
                    // Gender Validation
                    val selectedId: Int = radio_group_gender.getCheckedRadioButtonId()
                    radioButton = view.findViewById<View>(selectedId) as RadioButton
                    val gender: String = radioButton.text.toString()

                    // navigate to register 3 and pass values
                    var bundle: Bundle = Bundle()
                    bundle.putString("email", email)
                    bundle.putString("name", fullname)
                    bundle.putString("gender", gender)
                    bundle.putString("nric", nric)
                    bundle.putString("age", calAge.toString())
                    bundle.putString("nationality", country)

                    findNavController().navigate(R.id.register3, bundle)
                }

            }
        })

        //Back Button
        val register_back: ImageButton = view.findViewById<ImageButton>(R.id.register_back)
        register_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.register1)
        })


        return view
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            Register2Fragment()
    }
}