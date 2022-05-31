package com.example.coviddefender.navdrawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.navdrawerfragment.FragmentSettings

class FragmentSettings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_settings, container, false)

        val settings_back : ImageButton = view.findViewById<ImageButton>(R.id.settings_back)
        settings_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_settings_to_home)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentSettings()
    }
}