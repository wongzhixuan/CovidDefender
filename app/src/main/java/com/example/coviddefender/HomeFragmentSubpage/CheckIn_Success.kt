package com.example.coviddefender.HomeFragmentSubpage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.entity.CovidStatus
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.type.Date


class CheckIn_Success : Fragment() {
    lateinit var btn_check_out: MaterialButton
    lateinit var btn_back: ImageButton
    lateinit var tv_no_ppl: TextView
    lateinit var tv_check_in_location_name: TextView
    lateinit var tv_check_in_time: TextView
    lateinit var tv_check_in_person: TextView
    lateinit var tv_risk_status: TextView
    lateinit var tv_vaccine_status: TextView

    lateinit var location: String
    lateinit var time: String
    private lateinit var docId: String
    private lateinit var timestamp: String
    private lateinit var userId: String

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
        val view: View = inflater.inflate(R.layout.fragment_check_in__success, container, false)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
//            userId = currentUser.uid
            userId = "testing"
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("history").document("testing")

        // link widgets
        btn_check_out = view.findViewById<MaterialButton>(R.id.btn_check_out)
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)
        tv_no_ppl = view.findViewById(R.id.tv_no_ppl)
        tv_check_in_location_name = view.findViewById(R.id.tv_check_in_location_name)
        tv_check_in_time = view.findViewById(R.id.tv_check_in_time)
        tv_check_in_person = view.findViewById(R.id.tv_check_in_person)
        tv_risk_status = view.findViewById(R.id.tv_risk_status)
        tv_vaccine_status = view.findViewById(R.id.tv_vaccine_status)

        // get bundle
        var bundle: Bundle? = this.arguments
        if (bundle != null) {
            docId = bundle.getString("docId").toString()
            location = bundle.getString("location").toString()
            time = bundle.getString("time").toString()
//            timestamp = bundle.getString("timestamp").toString()
        }

        // get data from firebase and set up text view
        getAllData()

        // set up text view
        tv_check_in_location_name.text = location
        tv_check_in_time.text = time
        tv_check_in_person.text = currentUser.displayName


        btn_check_out.setOnClickListener {
            docRef.collection("historyItem").document(docId).update("IsCheckOut", true)
                .addOnSuccessListener {
                    setUpProgressDialog()
                }
                .addOnFailureListener { e ->
                    Log.w("CheckOut", "Error updating document", e)
                }

        }

        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_checkIn_Success_to_checkIn)
        })
        return view
    }

    private fun getAllData() {
        // calculate total people in the same place
        var total_ppl: Int = 0
        var IsSamePlace = false
        var docRefHistory = firestore.collection("history")
        docRefHistory.get().addOnSuccessListener { documents ->
            for (document in documents) {
                IsSamePlace = false
                docRefHistory.document(document.id).collection("historyItem")
                    .whereEqualTo("location", location).whereEqualTo("IsCheckOut", false).get()
                    .addOnSuccessListener {
                        IsSamePlace = true
                    }
                if (IsSamePlace) {
                    total_ppl += 1
                }
                Log.d("GetHistoryData", document.data.toString())
            }
        }

        // get risk status
        var covid_status: String = ""
        val docRefCovidStatus:DocumentReference = firestore.collection("covid_status").document(userId)
        docRefCovidStatus.get().addOnSuccessListener { document ->
            var covidStatus: CovidStatus = document.toObject<CovidStatus>()!!
            covid_status = covidStatus.covid_status
            // covid status
            tv_risk_status.text = covid_status

        }

        // get vaccine status
        var vaccine_status: String = ""
        val docRefVaccineStatus = firestore.collection("appointment").document(userId)
        docRefVaccineStatus.get().addOnSuccessListener { document ->
            if(document.exists()) {
                vaccine_status = document.get("vaccine_status").toString()
                // vaccine status
                tv_vaccine_status.text = vaccine_status
            }
        }
        // set up Text View
        tv_no_ppl.text = total_ppl.toString()

    }

    private fun setUpProgressDialog() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setCancelable(false)
        dialog.setTitle("Successfully Check Out")
        val time = Timestamp.now()
        val curr_time = time.toDate()
        dialog.setMessage("Location: $location\nAt $curr_time")
        dialog.setPositiveButton("Proceed", DialogInterface.OnClickListener { _, _ ->
            findNavController().navigate(R.id.action_checkIn_Success_to_checkIn)
        })
        dialog.create().show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CheckIn_Success()
    }
}