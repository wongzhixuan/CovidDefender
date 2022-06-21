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
import com.example.coviddefender.StepperAdapter.AppointmentAdapter
import com.transferwise.sequencelayout.SequenceLayout

class ViewAppointment : Fragment() {
    lateinit var tv_username: TextView
    lateinit var appointment_stepper: SequenceLayout
    lateinit var btn_back : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_view_appointment, container, false)
        // link widgets
        tv_username = view.findViewById(R.id.tv_username)
        // vertical stepper adapter
        appointment_stepper= view.findViewById(R.id.appointment_stepper)
        val items: ArrayList<AppointmentAdapter.MyItem> = arrayListOf<AppointmentAdapter.MyItem>(
            AppointmentAdapter.MyItem(false, "23 Feb 2021","Registered","" ),
            AppointmentAdapter.MyItem(true, "4 Mar 2021","Eligible for Vaccine?","")
        )
        appointment_stepper.setAdapter(AppointmentAdapter(items))

        // back button
        btn_back  = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_viewAppointment_to_appointment)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = ViewAppointment()
    }


}