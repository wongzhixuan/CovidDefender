package com.example.coviddefender.UserAuthentication

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Register3Fragment : Fragment() {
    lateinit var email: String
    lateinit var name: String
    lateinit var nric: String
    lateinit var gender: String
    lateinit var age: String
    lateinit var nationality: String

    lateinit var et_address: TextInputEditText
    lateinit var et_postcode: TextInputEditText
    lateinit var et_state: AutoCompleteTextView
    lateinit var register_back: ImageButton

    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_register3, container, false)
        // link widgets
        et_address = view.findViewById(R.id.et_address)
        et_postcode = view.findViewById(R.id.et_postcode)
        et_state = view.findViewById(R.id.et_state)
        register_back = view.findViewById(R.id.register_back_3)

        // get values passed from previous page
        email = arguments?.getString("email").toString()
        name = arguments?.getString("name").toString()
        nric = arguments?.getString("nric").toString()
        gender = arguments?.getString("gender").toString()
        age = arguments?.getString("age").toString()
        nationality = arguments?.getString("nationality").toString()

        // set up state
        val states = resources.getStringArray(R.array.state_items)
        val checkedItems_state: Array<Int> = arrayOf(-1)
        et_state.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(context)
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
            custom_dialog.show()
        })

        //next button
        val btn_next: Button = view.findViewById<Button>(R.id.btn_next)
        btn_next.setOnClickListener(View.OnClickListener {
            toNextPage()
        })

        //Back Button
        register_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.register1)
        })

        return view
    }

    private fun toNextPage() {

        val add: String = et_address.getText().toString()
        val postcode: String = et_postcode.getText().toString()
        val state: String = et_state.getText().toString()

        if (add.equals("") || postcode.equals("") || state.equals("")) {
            // Check if all fields are filled
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG)
                .show()
        } else {
            // navigate to next fragment and pass data
            var bundle: Bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("email", email)
            bundle.putString("gender", gender)
            bundle.putString("nric", nric)
            bundle.putString("address", add)
            bundle.putString("postcode", postcode)
            bundle.putString("age", age)
            bundle.putString("nationality", nationality)
            bundle.putString("state", state)
            findNavController().navigate(R.id.register4, bundle)
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() =
            Register3Fragment()
    }
}