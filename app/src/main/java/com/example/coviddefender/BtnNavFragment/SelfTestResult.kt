package com.example.coviddefender.BtnNavFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class SelfTestResult: Fragment() {

    // edittext or dropdown
    lateinit var et_date: TextInputEditText
    lateinit var et_result: AutoCompleteTextView
    lateinit var et_document: AutoCompleteTextView
    lateinit var et_address: TextInputEditText
    lateinit var et_postcode: TextInputEditText
    lateinit var et_state: AutoCompleteTextView

    // text input layout
    lateinit var txt_field_date: TextInputLayout
    lateinit var txt_field_test_result: TextInputLayout
    lateinit var txt_field_document: TextInputLayout
    lateinit var txt_field_address: TextInputLayout
    lateinit var txt_field_postcode: TextInputLayout
    lateinit var txt_field_state: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_self_test_result, container, false)

        //Link widgets
        et_date = view.findViewById(R.id.et_date)
        et_result = view.findViewById(R.id.et_result)
        et_document = view.findViewById(R.id.et_document)
        et_address = view.findViewById(R.id.et_address)
        et_postcode = view.findViewById(R.id.et_postcode)
        et_state = view.findViewById(R.id.et_state)


        // Date input
        et_date.setInputType(InputType.TYPE_NULL);
        et_date.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            val picker = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    et_date.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) }, year, month, day)
            picker.show()
        }

        // set up test result
        val testresult = resources.getStringArray(R.array.test_result_item)
        et_result = view.findViewById<AutoCompleteTextView>(R.id.et_result)
        // by default, selected item is null
        val checkedItems_testresult: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (state)
        et_result.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Test Result")
            builder.setSingleChoiceItems(testresult, checkedItems_testresult[0], DialogInterface.OnClickListener { dialogInterface, i ->
                checkedItems_testresult[0] = i
                et_result.setText(testresult[i])
                dialogInterface.dismiss()
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->  })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        // set up state
        val states = resources.getStringArray(R.array.state_items)
        et_state = view.findViewById<AutoCompleteTextView>(R.id.et_state)
        val checkedItems_state: Array<Int> = arrayOf(-1)
        et_state.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("State")
            builder.setIcon(R.drawable.ic_location_small)
            builder.setSingleChoiceItems(states, checkedItems_state[0], DialogInterface.OnClickListener { dialogInterface, i ->
                checkedItems_state[0] = i
                et_state.setText(states[i])
                dialogInterface.dismiss()
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->  })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        //submit button
        val btn_submit:Button = view.findViewById<Button>(R.id.btn_submit)
        btn_submit?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_test_result_to_home)
        })

        return view
    }

}