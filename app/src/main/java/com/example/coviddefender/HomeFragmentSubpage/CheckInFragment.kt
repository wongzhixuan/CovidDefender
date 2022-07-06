package com.example.coviddefender.HomeFragmentSubpage

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class CheckInFragment : Fragment() {
    // Initialize
    lateinit var btn_check_in: MaterialButton
    lateinit var profile_image: ShapeableImageView
    lateinit var tv_profile_name: TextView
    lateinit var tv_profile_id: TextView
    lateinit var covid_status_text: TextView
    lateinit var vaccine_status_text: TextView

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_check_in, container, false)
        // link widgets
        btn_check_in = view.findViewById<MaterialButton>(R.id.btn_check_in)
        profile_image = view.findViewById(R.id.profile_image)
        tv_profile_name = view.findViewById(R.id.tv_profile_name)
        tv_profile_id = view.findViewById(R.id.tv_profile_id)
        covid_status_text = view.findViewById(R.id.covid_status_text)
        vaccine_status_text = view.findViewById(R.id.vaccine_status_text)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("history").document("testing")

        btn_check_in.setOnClickListener(View.OnClickListener {
            var integrator: IntentIntegrator =
                IntentIntegrator.forSupportFragment(this@CheckInFragment)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR code")
            integrator.setBeepEnabled(false) // no beep sound when scanning
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.initiateScan()
        })


        // back button
        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_checkIn_to_home)

        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = CheckInFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
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
                            "DocumentSnapshot written with ID: " + documentReference.getId()
                        )
                        val datetime = time.toDate()
                        setProgressDialog(result.contents.toString(), datetime.toString())
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

    private fun setProgressDialog(location: String, time: String) {

        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setCancelable(false)
        dialog.setTitle("Successfully Check In")
        dialog.setMessage("Location: $location\nTime: $time")
        dialog.setPositiveButton("Proceed", DialogInterface.OnClickListener { _, _ ->
            val bundle = bundleOf("location" to location, "time" to time)
            findNavController().navigate(R.id.checkIn_Success, bundle)
        })
        dialog.create().show()
    }
}