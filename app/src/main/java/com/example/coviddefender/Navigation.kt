package com.example.coviddefender

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var userId: String

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
//            userId = currentUser.uid
            userId = "testing"
        } else {
            // if user is null, go to login
            reload()
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("history").document(userId)

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

            // call Intent Integrator
            var integrator: IntentIntegrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR code")
            integrator.setBeepEnabled(false) // no beep sound when scanning
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.initiateScan()
            isFromActivity = true

        })

        // set up some key sharedPreferences
        setUpFrequentlyUsedData()
    }

    override fun onRestart() {
        super.onRestart()
        setUpFrequentlyUsedData()
    }

    override fun onStart() {
        super.onStart()
        setUpFrequentlyUsedData()
    }

    override fun onResume() {
        super.onResume()
        setUpFrequentlyUsedData()
    }

    private fun setUpFrequentlyUsedData() {
        // get risk status
        var covid_status: String = ""
        val docRefCovidStatus = firestore.collection("covid_status").document(userId)
        docRefCovidStatus.get().addOnSuccessListener { document ->
            covid_status = document.get("covid_status").toString()
        }

        // get vaccine status
        var vaccine_status: String = ""
        val docRefVaccineStatus = firestore.collection("vaccine_status").document(userId)
        docRefVaccineStatus.get().addOnSuccessListener { document ->
            vaccine_status = document.get("vaccine_status").toString()
        }

        // get user id


        // store data to shared preferences
        var sharedPreferences: SharedPreferences = getSharedPreferences("UserStatus", MODE_PRIVATE)
        // Creating an Editor object to edit(write to the file)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()

        // store key and values fetch from firebase
        editor.putString("covid_status", covid_status)
        editor.putString("vaccine_status", vaccine_status)

        // commit changes
        editor.commit()
    }

    // return to login activity if user not logged in (firebase user == null)
    private fun reload() {
        val intent: Intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (isFromActivity) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                // if content is null
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    // get current time
                    var time = Timestamp.now()

                    // initialize hash map of check in data
                    var data = hashMapOf(
                        "IsCheckOut" to false,
                        "location" to result.contents.toString().trim(),
                        "time" to time
                    )

                    // create new record in history item
                    docRef.collection("historyItem")
                        .add(data)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                "AddHistory",
                                "DocumentSnapshot written with ID: " + documentReference.id
                            )
                            val datetime = time.toDate()
                            // show dialog
                            setProgressDialog(
                                result.contents.toString(),
                                datetime.toString(),
                                documentReference.id.toString()
                            )
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

    // display dialog of check in message
    private fun setProgressDialog(location: String, time: String, docId: String) {

        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle("Successfully Check In")
        dialog.setMessage("Location: $location\nTime: $time")
        dialog.setPositiveButton("Proceed", DialogInterface.OnClickListener { _, _ ->
            // prepare bundle with value to pass to next fragment
            val bundle = bundleOf("location" to location, "time" to time, "docId" to docId)
            // navigate to check in success fragment
            navController.navigate(R.id.checkIn_Success, bundle)
        })
        dialog.create().show()
    }
}