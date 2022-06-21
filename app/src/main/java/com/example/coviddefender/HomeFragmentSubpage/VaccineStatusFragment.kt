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


class VaccineStatusFragment : Fragment() {
    lateinit var btn_back : ImageButton
    lateinit var tv_username: TextView
    lateinit var tv_vaccine_status: TextView
    lateinit var btn_download: MaterialButton
    lateinit var tv_username_cert: TextView
    lateinit var tv_userIC_cert: TextView
    lateinit var tv_dose1_date: TextView
    lateinit var tv_dose1_manufacturer: TextView
    lateinit var tv_dose1_facility: TextView
    lateinit var tv_dose1_batch: TextView
    lateinit var tv_dose2_date: TextView
    lateinit var tv_dose2_manufacturer: TextView
    lateinit var tv_dose2_facility: TextView
    lateinit var tv_dose2_batch: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_vaccine_status, container, false)
        // link widgets
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)
        tv_username = view.findViewById(R.id.tv_username)
        tv_vaccine_status = view.findViewById(R.id.tv_vaccine_status)
        btn_download = view.findViewById(R.id.btn_download)
        tv_username_cert = view.findViewById(R.id.tv_username_cert)
        tv_userIC_cert = view.findViewById(R.id.tv_userIC_cert)
        tv_dose1_date = view.findViewById(R.id.tv_dose1_date)
        tv_dose1_manufacturer = view.findViewById(R.id.tv_dose1_manufacturer)
        tv_dose1_facility = view.findViewById(R.id.tv_dose1_facility)
        tv_dose1_batch = view.findViewById(R.id.tv_dose1_batch)
        tv_dose2_date = view.findViewById(R.id.tv_dose2_date)
        tv_dose2_manufacturer = view.findViewById(R.id.tv_dose2_manufacturer)
        tv_dose2_facility = view.findViewById(R.id.tv_dose2_facility)
        tv_dose2_batch = view.findViewById(R.id.tv_dose2_batch)

        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_vaccineStatus_to_home)

        })
        btn_download.setOnClickListener {
            val inflater = LayoutInflater.from(context)


        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = VaccineStatusFragment()
    }
}