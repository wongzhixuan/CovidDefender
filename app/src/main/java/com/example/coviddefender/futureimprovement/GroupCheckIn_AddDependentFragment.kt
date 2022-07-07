package com.example.coviddefender.futureimprovement

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class GroupCheckIn_AddDependentFragment : Fragment() {
    // edittext or dropdown
    lateinit var et_name: TextInputEditText
    lateinit var et_relation: AutoCompleteTextView
    lateinit var et_ic: TextInputEditText
    lateinit var et_address: TextInputEditText
    lateinit var et_postcode: TextInputEditText
    lateinit var et_state: AutoCompleteTextView

    // text input layout
    lateinit var txt_field_name: TextInputLayout
    lateinit var txt_field_relation: TextInputLayout
    lateinit var txt_field_ic: TextInputLayout
    lateinit var txt_field_address: TextInputLayout
    lateinit var txt_field_postcode: TextInputLayout
    lateinit var txt_field_state: TextInputLayout

    lateinit var relations: Array<String>
    lateinit var states: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    // Dropdown presentation 2 - exposed drop down
//    override fun onResume() {
//        super.onResume()
//        relations = resources.getStringArray(R.array.relation_item)
//        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(requireContext(),R.layout.relation_item,relations)
//        et_relation.setAdapter(arrayAdapter)
//    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_dependent, container, false)

        // link widgets
        et_name = view.findViewById<TextInputEditText>(R.id.et_name)
        et_ic = view.findViewById<TextInputEditText>(R.id.et_IC)
        et_address = view.findViewById<TextInputEditText>(R.id.et_address)
        et_postcode = view.findViewById<TextInputEditText>(R.id.et_postcode)
        txt_field_name = view.findViewById<TextInputLayout>(R.id.txt_field_name)
        txt_field_relation = view.findViewById<TextInputLayout>(R.id.txt_field_relation)
        txt_field_ic = view.findViewById<TextInputLayout>(R.id.txt_field_IC)
        txt_field_address = view.findViewById<TextInputLayout>(R.id.txt_field_address)
        txt_field_postcode = view.findViewById<TextInputLayout>(R.id.txt_field_postcode)
        txt_field_state = view.findViewById<TextInputLayout>(R.id.txt_field_state)
        // set up relation dropdown
        relations = resources.getStringArray(R.array.relation_item)
        et_relation = view.findViewById<AutoCompleteTextView>(R.id.et_relation)
        // by default, selected item is null
        val checkedItems: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (relations)
        et_relation.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Relation")
            builder.setIcon(R.drawable.ic_relation)
            builder.setSingleChoiceItems(
                relations,
                checkedItems[0],
                DialogInterface.OnClickListener { dialogInterface, i ->
                    checkedItems[0] = i
                    et_relation.setText(relations[i])
                    dialogInterface.dismiss()
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        // set up State dropdown
        states = resources.getStringArray(R.array.state_items)
        et_state = view.findViewById<AutoCompleteTextView>(R.id.et_state)
        // by default, selected item is null
        val checkedItems_state: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (state)
        et_state.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
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

        // submit button
        val btn_submit: MaterialButton = view.findViewById<MaterialButton>(R.id.btn_submit)
        btn_submit.setOnClickListener {
            if (validateInputs()) {
                // database pending
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_groupCheckIn_AddDependent_to_groupCheckIn)
            }

        }
        // cancel button - back to previous page
        val btn_cancel: MaterialButton = view.findViewById<MaterialButton>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            findNavController().navigate(R.id.action_groupCheckIn_AddDependent_to_groupCheckIn)
        }

        // back button
        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_groupCheckIn_AddDependent_to_groupCheckIn)
        })
        return view

    }

    private fun validateInputs(): Boolean {
        if (et_name.text.toString() == "") {
            txt_field_name.error = "Required Field*"
            return false
        } else {
            txt_field_name.error = null
        }
        if (et_relation.text.toString() == "") {
            txt_field_relation.error = "Required Field*"
            return false
        } else {
            txt_field_relation.error = null
        }
        if (et_ic.text.toString() == "") {
            txt_field_ic.error = "Required Field*"
            return false
        } else {
            txt_field_ic.error = null
        }
        if (et_address.text.toString() == "") {
            txt_field_address.error = "Required Field*"
            return false
        } else {
            txt_field_address.error = null
        }
        if (et_postcode.text.toString() == "") {
            txt_field_postcode.error = "Required Field*"
            return false
        } else {
            txt_field_postcode.error = null
        }
        if (et_state.text.toString() == "") {
            txt_field_state.error = "Required Field*"
            return false
        } else {
            txt_field_state.error = null
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        relations = resources.getStringArray(R.array.relation_item)
        // by default, selected item is null
        val checkedItems: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (relations)
        et_relation.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Relation")
            builder.setIcon(R.drawable.ic_relation)
            builder.setSingleChoiceItems(
                relations,
                checkedItems[0],
                DialogInterface.OnClickListener { dialogInterface, i ->
                    checkedItems[0] = i
                    et_relation.setText(relations[i])
                    dialogInterface.dismiss()
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })
        states = resources.getStringArray(R.array.state_items)
        // by default, selected item is null
        val checkedItems_state: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (state)
        et_state.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
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


    }

    companion object {

        @JvmStatic
        fun newInstance() = GroupCheckIn_AddDependentFragment()
    }
}



