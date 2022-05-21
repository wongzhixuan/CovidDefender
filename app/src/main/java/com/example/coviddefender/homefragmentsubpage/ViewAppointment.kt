package com.example.coviddefender.homefragmentsubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.stepper.AppointmentAdapter
import com.transferwise.sequencelayout.SequenceLayout

class ViewAppointment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_view_appointment, container, false)

        // vertical stepper adapter
//        val appointmentstepper: SequenceLayout = view.findViewById(R.id.appointment_stepper)
//        val items: ArrayList<AppointmentAdapter.MyItem> = arrayListOf<AppointmentAdapter.MyItem>(
//            AppointmentAdapter.MyItem(false, "23 Feb 2021","Registered","" ),
//            AppointmentAdapter.MyItem()
//        )
//        appointmentstepper.setAdapter(AppointmentAdapter(items))


        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
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