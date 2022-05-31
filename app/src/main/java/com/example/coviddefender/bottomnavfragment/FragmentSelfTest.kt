package com.example.coviddefender.bottomnavfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.coviddefender.R


class FragmentSelfTest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_self_test, container, false)

        val btn_start_test = view.findViewById<Button>(R.id.btn_start_test)
        btn_start_test.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_self_test_to_instruction)
        })


        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentSelfTest()
    }
}