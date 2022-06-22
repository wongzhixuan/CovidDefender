package com.example.coviddefender

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class Navigation : AppCompatActivity() {
    var isFromActivity: Boolean = false
     lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
         navController = this.findNavController(R.id.nav_host_fragment_activity_main)

        // find reference to bottom navigation view
        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)
        navView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        navView.menu.getItem(2).isChecked = true
        navView.menu.getItem(2).isEnabled = false

        // drawer

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(View.OnClickListener {
            navView.menu.getItem(2).isChecked = true
            navController.navigate(R.id.checkIn_Success)

            var integrator: IntentIntegrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR code")
            integrator.setBeepEnabled(false) // no beep sound when scanning
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.initiateScan()
            isFromActivity = true

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(isFromActivity) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned : " + result.getContents(), Toast.LENGTH_LONG)
                        .show();
                    navController.navigate(R.id.checkIn_Success)
                }
            }
        }
    }
}