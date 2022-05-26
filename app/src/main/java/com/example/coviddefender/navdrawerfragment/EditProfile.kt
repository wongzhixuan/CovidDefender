package com.example.coviddefender.navdrawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coviddefender.R
import com.example.coviddefender.homefragmentsubpage.Appointment

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
        return view
    }
    companion object {

        @JvmStatic
        fun newInstance() = EditProfile()
    }
}