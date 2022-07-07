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
import com.example.coviddefender.StepperAdapter.AppointmentAdapter
import com.example.coviddefender.entity.Vaccination
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.transferwise.sequencelayout.SequenceLayout
import java.text.SimpleDateFormat

class ViewAppointment : Fragment() {
    lateinit var tv_username: TextView
    lateinit var appointment_stepper: SequenceLayout
    lateinit var btn_back: ImageButton

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
        val view: View = inflater.inflate(R.layout.fragment_view_appointment, container, false)

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
        tv_username = view.findViewById(R.id.tv_username)

        // set up widgets with data from firebase
        setUpViewWithFirebase()

        // vertical stepper adapter
        appointment_stepper = view.findViewById(R.id.appointment_stepper)


        // back button
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_viewAppointment_to_appointment)

        })
        return view
    }

    private fun setUpViewWithFirebase() {
        tv_username.text = currentUser.displayName

        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                var vaccinaton: Vaccination = document.toObject<Vaccination>()!!
                val items: ArrayList<AppointmentAdapter.MyItem> = arrayListOf()
                var register_time_string: String
                var eligible_time_string: String
                var dose1_time_string: String
                var dose2_time_string: String
                var booster_time_string: String
                var dateFormat: SimpleDateFormat = SimpleDateFormat("d MMM yyyy")


                var register_time = vaccinaton.getRegistered_time().toDate()
                register_time_string = dateFormat.format(register_time).toString()

                if (vaccinaton.eligible_for_vaccine != null) {
                    var eligible_time = vaccinaton.eligible_for_vaccine.toDate()
                    eligible_time_string = dateFormat.format(eligible_time).toString()

                    if(vaccinaton.dose1.get("time") != null){
                        var time:Timestamp = vaccinaton.dose1.get("time") as Timestamp
                        var dose1_time = time.toDate()
                        dose1_time_string = dateFormat.format(dose1_time).toString()
                        if(vaccinaton.dose2.get("time")!=null){
                            var time:Timestamp = vaccinaton.dose2.get("time") as Timestamp
                            var dose2_time = time.toDate()
                            dose2_time_string = dateFormat.format(dose2_time).toString()

                            if(vaccinaton.booster.get("time") != null){
                                var time:Timestamp = vaccinaton.booster.get("time") as Timestamp
                                var booster_time = time.toDate()
                                booster_time_string = dateFormat.format(booster_time).toString()
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false, register_time_string,
                                        "Registered", ""
                                    )
                                )
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false,eligible_time_string,
                                        "Eligible for Vaccine?",""
                                    ))
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false, dose1_time_string,
                                        "1st Dose Appointment\n"+vaccinaton.dose1.get("location").toString(),
                                        vaccinaton.dose1.get("address").toString()
                                    )
                                )
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false, dose2_time_string,
                                        "2nd Dose Appointment\n"+vaccinaton.dose2.get("location").toString(),
                                        vaccinaton.dose2.get("address").toString()
                                    )
                                )
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false, booster_time_string,
                                        "Booster Appointment\n"+vaccinaton.booster.get("location").toString(),
                                        vaccinaton.booster.get("address").toString()
                                    )
                                )
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        true, booster_time_string,
                                        "Digital Certificated Issued",
                                        ""
                                    )
                                )
                            }
                            else{
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false, register_time_string,
                                        "Registered", ""
                                    )
                                )
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false,eligible_time_string,
                                        "Eligible for Vaccine?",""
                                    ))
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        false, dose1_time_string,
                                        "1st Dose Appointment\n"+vaccinaton.dose1.get("location").toString(),
                                        vaccinaton.dose1.get("address").toString()
                                    )
                                )
                                items.add(
                                    AppointmentAdapter.MyItem(
                                        true, dose2_time_string,
                                        "2nd Dose Appointment\n"+vaccinaton.dose2.get("location").toString(),
                                        vaccinaton.dose2.get("address").toString()
                                    )
                                )
                            }
                        }
                        else{
                            items.add(
                                AppointmentAdapter.MyItem(
                                    false, register_time_string, "Registered", ""
                                )
                            )
                            items.add(
                                AppointmentAdapter.MyItem(
                                    false,eligible_time_string,"Eligible for Vaccine?",""
                                ))
                            items.add(
                                AppointmentAdapter.MyItem(
                                    true, dose1_time_string, "1st Dose Appointment\n"+vaccinaton.dose1.get("location").toString(), vaccinaton.dose1.get("address").toString()
                                )
                            )
                        }
                    }
                    else{
                        items.add(
                            AppointmentAdapter.MyItem(
                                false, register_time_string, "Registered", ""
                            )
                        )
                        items.add(
                            AppointmentAdapter.MyItem(
                                true,eligible_time_string,"Eligible for Vaccine?",""
                            ))
                    }
                } else {
                    items.add(
                        AppointmentAdapter.MyItem(
                            true, register_time_string, "Registered", ""
                        )
                    )
                }
                appointment_stepper.setAdapter(AppointmentAdapter(items))

            }
        }



    }

    companion object {

        @JvmStatic
        fun newInstance() = ViewAppointment()
    }


}