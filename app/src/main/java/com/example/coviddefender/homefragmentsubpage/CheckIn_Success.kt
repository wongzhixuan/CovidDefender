package com.example.coviddefender.homefragmentsubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox


class CheckIn_Success : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_check_in__success, container, false)
        val btn_check_out : MaterialButton = view.findViewById<MaterialButton>(R.id.btn_check_out)
        btn_check_out.setOnClickListener {
            // update database (pending)
            findNavController().navigate(R.id.action_checkIn_Success_to_checkIn)
        }
        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_checkIn_Success_to_checkIn)
        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CheckIn_Success()
    }
}