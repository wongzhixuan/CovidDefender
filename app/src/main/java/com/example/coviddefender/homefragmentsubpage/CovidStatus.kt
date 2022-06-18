package com.example.coviddefender.homefragmentsubpage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class CovidStatus : Fragment() {

    lateinit var btn_refresh: ImageButton
    lateinit var tv_update_time: TextView
    lateinit var tv_covid_status: TextView
    lateinit var view_status_color: View
    lateinit var covidstatus_qr_code: ImageView
    lateinit var tv_location_risk: TextView
    lateinit var tv_dependent_risk: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_covid_status, container, false)
        // set up view
        // get previous fetch data from roomDB

        // link widgets
        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_refresh = view.findViewById<ImageButton>(R.id.btn_refresh)
        tv_update_time = view.findViewById<TextView>(R.id.tv_update_time)
        tv_covid_status = view.findViewById<TextView>(R.id.tv_covid_status)
        view_status_color = view.findViewById<View>(R.id.view_status_color)
        covidstatus_qr_code = view.findViewById<ImageView>(R.id.covidstatus_qr_code)
        tv_location_risk = view.findViewById<TextView>(R.id.tv_location_risk)
        tv_dependent_risk = view.findViewById<TextView>(R.id.tv_dependent_risk)

        // btn_refresh pressed
        btn_refresh.setOnClickListener {

            // fetch data from Firebase

            // update tv_update_time
            var current = LocalDateTime.now()
            var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            var update_time = current.format(formatter)
            tv_update_time.setText(update_time)

        // update roomDB

        }

        // btn_back pressed: return to homepage
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_covid_status_to_home)

        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = CovidStatus()
    }
}