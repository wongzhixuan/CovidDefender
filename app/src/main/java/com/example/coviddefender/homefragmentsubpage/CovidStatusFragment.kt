package com.example.coviddefender.homefragmentsubpage


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.db.covidstatus.CovidStatus
import com.example.coviddefender.db.covidstatus.CovidStatusViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.HashMap


class CovidStatusFragment : Fragment() {
    val TAG : String = "CovidStatus"

    lateinit var btn_refresh: ImageButton
    lateinit var tv_update_time: TextView
    lateinit var tv_covid_status: TextView
    lateinit var view_status_color: View
    lateinit var covidstatus_qr_code: ImageView
    lateinit var tv_location_risk: TextView
    lateinit var tv_dependent_risk: TextView
    private lateinit var auth: FirebaseAuth
    // Cloud Firestore
    val covidstatus_db = Firebase.firestore
    lateinit var docRef: DocumentReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_covid_status, container, false)
        // initialize firebase
        auth = Firebase.auth
        val currentUser = auth.currentUser
        var userId: String = currentUser?.uid.toString()
        // for testing
        userId = "N8wofTpWCeuVPBSJHecL"
        docRef = covidstatus_db.collection("covid_status").document(userId)

        // link widgets
        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_refresh = view.findViewById<ImageButton>(R.id.btn_refresh)
        tv_update_time = view.findViewById<TextView>(R.id.tv_update_time)
        tv_covid_status = view.findViewById<TextView>(R.id.tv_covid_status)
        view_status_color = view.findViewById<View>(R.id.view_status_color)
        covidstatus_qr_code = view.findViewById<ImageView>(R.id.covidstatus_qr_code)
        tv_location_risk = view.findViewById<TextView>(R.id.tv_location_risk)
        tv_dependent_risk = view.findViewById<TextView>(R.id.tv_dependent_risk)

        // set up view
        // fetch data from firebase
        docRef.addSnapshotListener { value, error ->
            if(value!!.exists()){
                tv_update_time.text = value.getString("update_time")
                tv_covid_status.text = value.getString("covid_status")
                tv_location_risk.text = value.getString("location_risk")
                tv_dependent_risk.text = value.getString("dependent_risk")
            }
            else{
                // document not exist
                Log.d(TAG, "Document Not Exist!")
            }
        }
        // btn_refresh pressed
        btn_refresh.setOnClickListener {
            // fetch data from firebase
            docRef.addSnapshotListener { value, error ->
                if(value!!.exists()){
                    Log.d(TAG, value.getString("update_time") +" "+ value.getString("covid_status") +" "+ value.getString("location_risk") +" "+ value.getString("dependent_risk"))
                    tv_update_time.text = value.getString("update_time")
                    tv_covid_status.text = value.getString("covid_status")
                    tv_location_risk.text = value.getString("location_risk")
                    tv_dependent_risk.text = value.getString("dependent_risk")
                }
                else{
                    // document not exist
                    Log.d(TAG, "Document Not Exist!")
                }
            }
            // update tv_update_time
            var current = LocalDateTime.now()
            var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            var update_time = current.format(formatter)
            tv_update_time.text = update_time

            // update data to Firebase
            val updated = hashMapOf(
                "update_time" to update_time.toString()
            )
            docRef.update(updated as Map<String, Any>).addOnSuccessListener{
                unused ->
                Log.d(TAG, update_time + "updated")
            }.addOnFailureListener {
                Log.d(TAG, "Update Failed!")
            }
        }

        // btn_back pressed: return to homepage
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_covid_status_to_home)

        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = CovidStatusFragment()
    }
}