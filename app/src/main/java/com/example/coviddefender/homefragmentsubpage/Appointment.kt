package com.example.coviddefender.homefragmentsubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.recyclerview.VaccineInfoAdapter
import com.example.coviddefender.recyclerview.Vaccine_Info
import com.google.android.material.button.MaterialButton


class Appointment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_appointment, container, false)

        // dummy data for recycler view
        var infos: ArrayList<Vaccine_Info> = arrayListOf(
            Vaccine_Info(
                R.drawable.vaccine_illustration,
                "How does covid-19 vaccine works?"
            ),
            Vaccine_Info(
                R.drawable.myths_about_covid_vaccine,
                "Myths about Covid-19 vaccine"
            ),
            Vaccine_Info(
                R.drawable.vaccine_illustration,
                "How does covid-19 vaccine works?"
            ),
            Vaccine_Info(
                R.drawable.myths_about_covid_vaccine,
                "Myths about Covid-19 vaccine"
            )
        )
        // Vaccine Info Recycler view
        val vaccineinfo_recyclerview: RecyclerView? = view.findViewById<RecyclerView>(R.id.vaccine_info_recyclerview)
        vaccineinfo_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // adopt data to recycler view using adapter
        vaccineinfo_recyclerview?.adapter = VaccineInfoAdapter(infos)

        // btn_register
        val btn_register: MaterialButton = view.findViewById<MaterialButton>(R.id.btn_register_vaccine)
        btn_register.setOnClickListener {

        }
        // btn_view_appointment
        val btn_view_appoitment: MaterialButton = view.findViewById<MaterialButton>(R.id.btn_view_appointment)
        btn_view_appoitment.setOnClickListener {
            findNavController().navigate(R.id.action_appointment_to_viewAppointment)
        }

        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_appointment_to_home)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = Appointment()
    }
}