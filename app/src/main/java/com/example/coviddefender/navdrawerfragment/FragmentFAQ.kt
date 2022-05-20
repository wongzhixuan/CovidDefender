package com.example.coviddefender.navdrawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coviddefender.R
import com.example.coviddefender.navdrawerfragment.FragmentFAQ

class FragmentFAQ : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentFAQ()
    }
}