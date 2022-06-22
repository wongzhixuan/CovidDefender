package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton


class CheckIn_Success : Fragment() {
    lateinit var btn_check_out : MaterialButton
    lateinit var btn_back : ImageButton
    lateinit var tv_no_ppl: TextView
    lateinit var tv_check_in_location_name: TextView
    lateinit var tv_check_in_time: TextView
    lateinit var tv_check_in_person: TextView
    lateinit var tv_risk_status: TextView
    lateinit var tv_vaccine_status: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_check_in__success, container, false)
        // link widgets
        btn_check_out = view.findViewById<MaterialButton>(R.id.btn_check_out)
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)
        tv_no_ppl = view.findViewById(R.id.tv_no_ppl)
        tv_check_in_location_name = view.findViewById(R.id.tv_check_in_location_name)
        tv_check_in_time = view.findViewById(R.id.tv_check_in_time)
        tv_check_in_person = view.findViewById(R.id.tv_check_in_person)
        tv_risk_status = view.findViewById(R.id.tv_risk_status)
        tv_vaccine_status = view.findViewById(R.id.tv_vaccine_status)
        btn_check_out.setOnClickListener {
            // update database (pending)
            findNavController().navigate(R.id.action_checkIn_Success_to_checkIn)
        }

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