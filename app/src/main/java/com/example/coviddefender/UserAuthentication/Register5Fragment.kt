package com.example.coviddefender.UserAuthentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R


class Register5Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register5, container, false)
        //next button
        val btn_login: Button = view.findViewById(R.id.btn_login)
        btn_login.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        })

        val register_back: ImageButton = view.findViewById(R.id.register_back)
        register_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.register1)
        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            Register5Fragment()
    }
}