package com.example.coviddefender.UserAuthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.coviddefender.R

class RegisterBaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_base)

        var navController:NavController = this.findNavController(R.id.nav_host_fragment_activity_register)


    }
}