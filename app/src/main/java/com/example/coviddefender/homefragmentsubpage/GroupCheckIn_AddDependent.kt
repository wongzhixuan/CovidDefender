package com.example.coviddefender.homefragmentsubpage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.textfield.TextInputLayout


class GroupCheckIn_AddDependent : Fragment() {
    lateinit var relations: Array<String>
    lateinit var et_relation: AutoCompleteTextView

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
        val view:View = inflater.inflate(R.layout.fragment_add_dependent, container, false)

        // set up relation dropdown
        relations = resources.getStringArray(R.array.relation_item)
        et_relation = view.findViewById<AutoCompleteTextView>(R.id.et_relation)
        // by default, selected item is null
        val checkedItems :Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (relations)
        et_relation.setOnClickListener(View.OnClickListener {
            var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Relation")
            builder.setIcon(R.drawable.ic_relation)
            builder.setSingleChoiceItems(relations, checkedItems[0], DialogInterface.OnClickListener { dialogInterface, i ->
                checkedItems[0] = i
                et_relation.setText(relations[i])
                dialogInterface.dismiss()
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->  })
            val custom_dialog: AlertDialog = builder.create()
//            custom_dialog.window?.setBackgroundDrawableResource()
            custom_dialog.show()
        })

        // set up State dropdown
        val states = resources.getStringArray(R.array.state_items)
        val et_state: AutoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.et_state)
        // by default, selected item is null
        val checkedItems_state: Array<Int> = arrayOf(-1)
        // dropdown presentation 1: AlertDialog (state)
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

        // back button
        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_groupCheckIn_AddDependent_to_groupCheckIn)
        })
        return view

    }

    companion object {

        @JvmStatic
        fun newInstance() =  GroupCheckIn_AddDependent()
    }
}



