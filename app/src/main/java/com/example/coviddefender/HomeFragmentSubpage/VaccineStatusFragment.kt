package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.entity.Vaccination
import com.google.firebase.Timestamp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class VaccineStatusFragment : Fragment() {
    lateinit var btn_back: ImageButton
    lateinit var tv_username: TextView
    lateinit var tv_vaccine_status: TextView


    lateinit var tv_username_cert: TextView
    lateinit var tv_userIC_cert: TextView
    lateinit var tv_dose1_date: TextView
    lateinit var tv_dose1_manufacturer: TextView
    lateinit var tv_dose1_facility: TextView
    lateinit var tv_dose1_batch: TextView
    lateinit var tv_dose2_date: TextView
    lateinit var tv_dose2_manufacturer: TextView
    lateinit var tv_dose2_facility: TextView
    lateinit var tv_dose2_batch: TextView

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String

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
        val view: View = inflater.inflate(R.layout.fragment_vaccine_status, container, false)
        // link widgets
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)
        tv_username = view.findViewById(R.id.tv_username)
        tv_vaccine_status = view.findViewById(R.id.tv_vaccine_status)
        tv_username_cert = view.findViewById(R.id.tv_username_cert)
        tv_userIC_cert = view.findViewById(R.id.tv_userIC_cert)
        tv_dose1_date = view.findViewById(R.id.tv_dose1_date)
        tv_dose1_manufacturer = view.findViewById(R.id.tv_dose1_manufacturer)
        tv_dose1_facility = view.findViewById(R.id.tv_dose1_facility)
        tv_dose1_batch = view.findViewById(R.id.tv_dose1_batch)
        tv_dose2_date = view.findViewById(R.id.tv_dose2_date)
        tv_dose2_manufacturer = view.findViewById(R.id.tv_dose2_manufacturer)
        tv_dose2_facility = view.findViewById(R.id.tv_dose2_facility)
        tv_dose2_batch = view.findViewById(R.id.tv_dose2_batch)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
//            userId = currentUser.uid
            userId = "testing"
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("appointment").document(userId)

        // get data from firebase and set up text view
        setUpTextView()

        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_vaccineStatus_to_home)

        })

        return view
    }

    private fun setUpTextView() {
        var name: String? = currentUser.displayName
        var id: String = ""
        var docRefUsers: DocumentReference = firestore.collection("users").document(currentUser.uid)
        docRefUsers.get().addOnSuccessListener { document ->
            id = document.get("nric").toString()
            tv_userIC_cert.text = id
        }


        // get data of vaccination from firebase
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                var vaccinaton: Vaccination = document.toObject<Vaccination>()!!

                tv_username.text = name
                tv_username_cert.text = name

                // dose 1
                var dose1: Map<String, Object> = document.get("dose1") as Map<String, Object>
                var time: Timestamp = dose1.get("time") as Timestamp
                var dose1_time = time.toDate().toString()
                tv_dose1_date.text = dose1_time
                tv_dose1_batch.text = dose1.get("batch").toString()
                tv_dose1_manufacturer.text = dose1.get("manufacturer").toString()
                tv_dose1_facility.text = dose1.get("location").toString()

                // dose 2
                var dose2: Map<String, Object> = document.get("dose2") as Map<String, Object>
                var time_dose2: Timestamp = dose2.get("time") as Timestamp
                var dose2_time = time_dose2.toDate().toString()
                tv_dose2_date.text = dose2_time
                tv_dose2_batch.text = dose2.get("batch").toString()
                tv_dose2_manufacturer.text = dose2.get("manufacturer").toString()
                tv_dose2_facility.text = dose2.get("location").toString()

                tv_vaccine_status.text = document.get("vaccine_status").toString()
            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = VaccineStatusFragment()
    }
}