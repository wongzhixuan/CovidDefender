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
import com.example.coviddefender.entity.VaccineCert
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class VaccineStatusFragment : Fragment() {
    lateinit var btn_back: ImageButton
    lateinit var tv_username: TextView
    lateinit var tv_vaccine_status: TextView
//    lateinit var btn_download: MaterialButton
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
//        btn_download = view.findViewById(R.id.btn_download)
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
        docRef = firestore.collection("vaccine_status").document(userId)

        // get data from firebase and set up text view
        setUpTextView()

        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_vaccineStatus_to_home)

        })
//        btn_download.setOnClickListener {
//            val inflater = LayoutInflater.from(context)
//
//
//        }
        return view
    }

    private fun setUpTextView() {
        var name: String? = currentUser.displayName
        var id: String = ""
        var docRefUsers :DocumentReference = firestore.collection("users").document(currentUser.uid)
        docRefUsers.get().addOnSuccessListener {
            document->
            id = document.get("nric").toString()
            tv_userIC_cert.text = id
        }

        docRef.get().addOnSuccessListener { document ->
            var vaccineCert: VaccineCert = VaccineCert(
                name, id,
                document.getTimestamp("dose1_date")?.toDate().toString(),
                document.get("dose1_manufacturer").toString(),
                document.get("dose1_facility").toString(),
                document.get("dose1_batch").toString(),
                document.getTimestamp("dose2_date")?.toDate().toString(),
                document.get("dose2_manufacturer").toString(),
                document.get("dose2_facility").toString(),
                document.get("dose2_batch").toString(),
            )
            tv_username.text = name
            tv_username_cert.text = name

            // dose 1
            tv_dose1_date.text = vaccineCert.dose1_date
            tv_dose1_batch.text = vaccineCert.dose1_batch
            tv_dose1_manufacturer.text = vaccineCert.dose1_manufacturer
            tv_dose1_facility.text = vaccineCert.dose1_facility

            // dose 2
            tv_dose2_batch.text = vaccineCert.dose2_batch
            tv_dose2_date.text = vaccineCert.dose2_date
            tv_dose2_manufacturer.text = vaccineCert.dose2_manufacturer
            tv_dose2_facility.text = vaccineCert.dose2_facility

            tv_vaccine_status.text = document.get("vaccine_status").toString()

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = VaccineStatusFragment()
    }
}