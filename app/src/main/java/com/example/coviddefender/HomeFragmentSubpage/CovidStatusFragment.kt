package com.example.coviddefender.HomeFragmentSubpage



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.entity.CovidStatus
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject



class CovidStatusFragment : Fragment() {
    val TAG: String = "CovidStatus"

    lateinit var btn_refresh: ImageButton
    lateinit var tv_update_time: TextView
    lateinit var tv_covid_status: TextView
    lateinit var view_status_color: View
    lateinit var covidstatus_qr_code: ImageView
    lateinit var tv_location_risk: TextView
    lateinit var tv_dependent_risk: TextView

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference

    // Covid Status data
    lateinit var covidStatus: CovidStatus



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_covid_status, container, false)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("covid_status").document("testing")

        // link widgets
        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_refresh = view.findViewById<ImageButton>(R.id.btn_refresh)
        tv_update_time = view.findViewById<TextView>(R.id.tv_update_time)
        tv_covid_status = view.findViewById<TextView>(R.id.tv_covid_status)
        view_status_color = view.findViewById<View>(R.id.view_status_color)
        covidstatus_qr_code = view.findViewById<ImageView>(R.id.covidstatus_qr_code)
        tv_location_risk = view.findViewById<TextView>(R.id.tv_location_risk)
        tv_dependent_risk = view.findViewById<TextView>(R.id.tv_dependent_risk)

        // set up view
        // fetch data from firebase
        getData()

        // btn_refresh pressed
        btn_refresh.setOnClickListener {
            // fetch data from firebase
            getData()
            // update tv_update_time
            val timestamp = Timestamp.now()
            val update_time = timestamp.toDate()

            tv_update_time.text = update_time.toString()

            // update data to Firebase
            val updated = mapOf(
                "update_time" to timestamp
            )
            docRef.update(updated).addOnSuccessListener { unused ->
                Log.d(TAG, "Updated: $update_time")
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Update Failed!", exception)
            }
        }

        // btn_back pressed: return to homepage
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_covid_status_to_home)

        })
        return view
    }

    private fun getData()
    {
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    covidStatus = documentSnapshot.toObject<CovidStatus>()!!
                    var timestamp: Timestamp = covidStatus.update_time
                    var update_time = timestamp.toDate()
                    tv_update_time.text = update_time.toString()
                    tv_covid_status.text = covidStatus.covid_status
                    tv_dependent_risk.text = covidStatus.dependent_risk
                    tv_location_risk.text = covidStatus.location_risk
                    when (covidStatus.covid_status) {
                        "Low Risk" -> {
                            view_status_color.setBackgroundResource(R.color.light_green)
                        }
                        "High Risk" -> {
                            view_status_color.setBackgroundResource(R.color.light_coral)
                        }
                        else -> {
                            view_status_color.setBackgroundResource(R.color.light_orange)
                        }
                    }
                }
                else {
                    Log.d(TAG, "Document Not Exist!")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Failed: ", exception)
            }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CovidStatusFragment()
    }
}