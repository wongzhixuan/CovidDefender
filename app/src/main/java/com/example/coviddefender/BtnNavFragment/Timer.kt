package com.example.coviddefender.BtnNavFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R

class Timer : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_timer, container, false)

        val btn_next = view.findViewById<Button>(R.id.btn_next)
        btn_next?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_timer_to_test_result)
        })

        val btn_stop_timer = view.findViewById<Button>(R.id.btn_stop_timer)
        btn_stop_timer?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_timer_to_instruction)
        })

        // back button
        val self_test_back = view.findViewById<ImageButton>(R.id.self_test_back)
        self_test_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_timer_to_instruction)
        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = Timer()
    }
}