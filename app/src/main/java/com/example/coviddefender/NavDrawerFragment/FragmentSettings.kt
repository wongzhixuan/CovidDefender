package com.example.coviddefender.NavDrawerFragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.switchmaterial.SwitchMaterial

class FragmentSettings : Fragment() {

    lateinit var notification_setting_switch : SwitchMaterial


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_settings, container, false)

        notification_setting_switch = view.findViewById(R.id.notification_setting_switch)
        notification_setting_switch.setOnCheckedChangeListener() { _, isChecked ->
            val message = if (isChecked) "Notification has turned on" else "Notification has turned off"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        val settings_back : ImageButton = view.findViewById<ImageButton>(R.id.settings_back)
        settings_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_settings_to_home)

        })
        return view
    }



companion object {

        @JvmStatic
        fun newInstance() = FragmentSettings()
    }
}