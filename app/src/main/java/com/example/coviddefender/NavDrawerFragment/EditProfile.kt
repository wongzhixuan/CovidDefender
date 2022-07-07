package com.example.coviddefender.NavDrawerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton


class EditProfile : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        val btn_profile_details: MaterialButton =
            view.findViewById<MaterialButton>(R.id.btn_profile_details)
        btn_profile_details.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editprofile_to_editdetails)

        })

        val btn_profile_change_password: MaterialButton =
            view.findViewById<MaterialButton>(R.id.btn_profile_change_password)
        btn_profile_change_password.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editprofile_to_changepassword)

        })

        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editprofile_to_home)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = EditProfile()
    }
}