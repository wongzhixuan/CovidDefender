package com.example.coviddefender.navdrawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton

class ChangePassword : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_change_password, container, false)

        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back )
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_chgpassword_to_editprofile)

        })
        return view
    }
    companion object {

        @JvmStatic
        fun newInstance() = ChangePassword()
    }
}