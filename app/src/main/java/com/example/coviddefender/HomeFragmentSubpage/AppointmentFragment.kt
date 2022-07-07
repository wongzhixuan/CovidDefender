package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.VaccineInfoAdapter
import com.example.coviddefender.entity.Vaccination
import com.example.coviddefender.entity.Vaccine_Info
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class AppointmentFragment : Fragment() {

    // widgets
    lateinit var btn_view_appoitment: MaterialButton
    lateinit var btn_register: MaterialButton
    lateinit var vaccineinfo_recyclerview: RecyclerView
    lateinit var btn_back : ImageButton

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
        val view: View = inflater.inflate(R.layout.fragment_appointment, container, false)

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

        // link widgets
        vaccineinfo_recyclerview = view.findViewById<RecyclerView>(R.id.vaccine_info_recyclerview)
        btn_register = view.findViewById<MaterialButton>(R.id.btn_register_vaccine)
        btn_view_appoitment = view.findViewById<MaterialButton>(R.id.btn_view_appointment)
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)

        // fetch data
        getData()

        // dummy data for recycler view
        var infos: ArrayList<Vaccine_Info> = arrayListOf(
            Vaccine_Info(
                R.drawable.vaccine_illustration,
                "How does covid-19 vaccine works?"
            ),
            Vaccine_Info(
                R.drawable.myths_about_covid_vaccine,
                "Myths about Covid-19 vaccine"
            ),
            Vaccine_Info(
                R.drawable.vaccine_illustration,
                "How does covid-19 vaccine works?"
            ),
            Vaccine_Info(
                R.drawable.myths_about_covid_vaccine,
                "Myths about Covid-19 vaccine"
            )
        )
        // Vaccine Info Recycler view
        vaccineinfo_recyclerview.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // adopt data to recycler view using adapter
        vaccineinfo_recyclerview.adapter = VaccineInfoAdapter(infos)

        // btn_register
        btn_register.setOnClickListener {
            // get current time
            var time = Timestamp.now()
            var data = hashMapOf(
                "IsRegistered" to true,
                "registered" to time
            )
            docRef.update(data as Map<String, Any>).addOnSuccessListener {
                Toast.makeText(context,"Successfully Registered",Toast.LENGTH_SHORT).show()
                btn_register.isEnabled = false
            }

        }
        // btn_view_appointment
        btn_view_appoitment.setOnClickListener {
            findNavController().navigate(R.id.action_appointment_to_viewAppointment)
        }

        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_appointment_to_home)

        })
        return view
    }

    private fun getData() {
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                var vaccinaton: Vaccination = document.toObject<Vaccination>()!!
                if(vaccinaton.getIsRegistered() == true) {
                    btn_register.isEnabled = false
                }
                else{
                    btn_register.isEnabled = true
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = AppointmentFragment()
    }
}