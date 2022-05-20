package com.example.coviddefender.bottomnavfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coviddefender.R


class FragmentSelfTest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_self_test, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentSelfTest()
    }
}