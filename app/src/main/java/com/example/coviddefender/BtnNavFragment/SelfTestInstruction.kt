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

class SelfTestInstruction : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_self_test_instruction, container, false)
        // start timer
        val btn_start_timer: Button = view.findViewById<Button>(R.id.btn_start_timer)
        btn_start_timer?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_instruction_to_timer)
        })
        // back button
        val self_test_back_2: ImageButton = view.findViewById<ImageButton>(R.id.self_test_back_2)
        self_test_back_2?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_instruction_to_self_test)

        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = SelfTestInstruction()
    }
}