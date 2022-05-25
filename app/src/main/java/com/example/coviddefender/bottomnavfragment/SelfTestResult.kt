package com.example.coviddefender.bottomnavfragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class SelfTestResult: Fragment() {

    // edittext or dropdown
    lateinit var et_date: TextInputEditText
    lateinit var et_result: AutoCompleteTextView

    // text input layout
    lateinit var txt_field_date: TextInputLayout
    lateinit var txt_field_test_result: TextInputLayout

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
        et_date = view.findViewById(R.id.et_date);
        et_result = view.findViewById(R.id.et_result);

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
        return view
    }

}