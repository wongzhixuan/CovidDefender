package com.example.coviddefender

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coviddefender.UserAuthentication.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class Navigation : AppCompatActivity() {
    var isFromActivity: Boolean = false
    lateinit var navController: NavController

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
        } else {
            // if user is null, go to login
            reload()
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("history").document("testing")

        // Bottom Navigation
        // get navigation host fragment, instantiate navController using NavHostFragment
        navController = this.findNavController(R.id.nav_host_fragment_activity_main)

        // find reference to bottom navigation view
        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)
        navView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        navView.menu.getItem(2).isChecked = true
        navView.menu.getItem(2).isEnabled = false

        // qr scanner
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(View.OnClickListener {
            navView.menu.getItem(2).isChecked = true

            var integrator: IntentIntegrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR code")
            integrator.setBeepEnabled(false) // no beep sound when scanning
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.initiateScan()
            isFromActivity = true

        })

    }

    private fun reload() {
        val intent: Intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (isFromActivity) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    var time = Timestamp.now()
                    var data = hashMapOf(
                        "IsCheckOut" to false,
                        "location" to result.contents.toString().trim(),
                        "time" to time
                    )

                    docRef.collection("historyItem")
                        .add(data)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                "AddHistory",
                                "DocumentSnapshot written with ID: " + documentReference.id
                            )
                            val datetime = time.toDate()
                            setProgressDialog(result.contents.toString(), datetime.toString(), documentReference.id.toString())
                        }
                        .addOnFailureListener { e ->
                            Log.w(
                                "AddHistory",
                                "Error adding document" +
                                e.message.toString()
                            )
                        }
                }
            }
        }
    }

    private fun setProgressDialog(location: String, time: String, docId: String) {

        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle("Successfully Check In")
        dialog.setMessage("Location: $location\nTime: $time")
        dialog.setPositiveButton("Proceed", DialogInterface.OnClickListener{ _, _ ->
            val bundle = bundleOf("location" to location, "time" to time, "docId" to docId)
            navController.navigate(R.id.checkIn_Success, bundle)
        })
        dialog.create().show()
    }
}