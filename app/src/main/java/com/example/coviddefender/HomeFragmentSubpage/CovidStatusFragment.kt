package com.example.coviddefender.HomeFragmentSubpage



import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
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
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class CovidStatusFragment : Fragment() {
    val TAG: String = "CovidStatus"

    lateinit var btn_refresh: ImageButton
    lateinit var tv_update_time: TextView
    lateinit var tv_covid_status: TextView
    lateinit var view_status_color: View
    lateinit var covidstatus_qr_code: ImageView
    lateinit var tv_location_risk: TextView
    lateinit var tv_dependent_risk: TextView
    lateinit var progressBar: ProgressBar

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference

    // Covid Status data
    private lateinit var covidStatus: CovidStatus
    private lateinit var update_time_string: String
    private lateinit var covid_status_string: String
    private lateinit var location_risk_string: String
    private lateinit var dependent_risk_string:String

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
        progressBar = view.findViewById(R.id.progressBar)

        // set up view
        // fetch data from firebase
        getData()

        // generate QR code
        generateQRCode()

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

    private fun generateQRCode() {
        // load progress bar
        progressBar.visibility = View.VISIBLE
        // get data
        covid_status_string = covidStatus.covid_status
        update_time_string = covidStatus.update_time.toDate().toString()
        location_risk_string = covidStatus.location_risk
        dependent_risk_string = covidStatus.dependent_risk
        var userName = currentUser.displayName

        var QR_info: String = "User: $userName, Covid Status: $covid_status_string, Location Risk: $location_risk_string, High Dependent Risk: $dependent_risk_string"
        // initialize multi format writer
        var writer:MultiFormatWriter = MultiFormatWriter()

        try {
            // Initialize Bit matrix
            var matrix: BitMatrix = writer.encode(QR_info, BarcodeFormat.QR_CODE, 250, 250)
            // Initialize barcode encoder
            var encoder: BarcodeEncoder = BarcodeEncoder()
            // Initialize Bitmap
            var bitmap: Bitmap = encoder.createBitmap(matrix)
            // set bitmap on image view
            covidstatus_qr_code.setImageBitmap(bitmap)
            progressBar.visibility = View.GONE
        }
        catch (exception:WriterException){
            Log.d("GenerateQrCode", exception.message.toString())

        }
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
        generateQRCode()
    }

    override fun onStart() {
        super.onStart()
        getData()
        generateQRCode()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CovidStatusFragment()
    }
}