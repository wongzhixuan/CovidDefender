package com.example.coviddefender

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class RegisterActivity : AppCompatActivity() {

    // Initialize widgets
    lateinit var et_contact_no: TextInputEditText
    lateinit var et_verification_code: TextInputEditText
    lateinit var txt_field_contact_no : TextInputLayout
    lateinit var txt_field_verification_code : TextInputLayout
    lateinit var btn_verify: Button
    lateinit var btn_continue: Button
    lateinit var login_link : TextView
    lateinit var resend_link : TextView

    private var storedVerificationId: String? = ""

    // if code sending failed, will used to resend
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verification: String? = null

    // Firebase
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    private val TAG = "MAIN_TAG"

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)

        et_contact_no = findViewById(R.id.et_contact_no)
        et_verification_code = findViewById(R.id.et_verification_code)
        txt_field_contact_no = findViewById(R.id.txt_field_contact_no)
        txt_field_verification_code = findViewById(R.id.txt_field_verification_code)
        btn_verify = findViewById(R.id.btn_verify)
        resend_link = findViewById(R.id.resend_link)

        et_contact_no.visibility = View.VISIBLE
        et_verification_code.visibility = View.GONE
        txt_field_verification_code.visibility = View.GONE
        btn_verify.visibility = View.GONE
        resend_link.visibility = View.GONE

        // In the onCreate() method, initialize the FirebaseAuth instance.
        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
                signUpWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                progressDialog.dismiss()
                Toast.makeText(this@RegisterActivity,"${e.message}",Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
                progressDialog.dismiss()

                //hide phone layout, show code layout
                et_verification_code.visibility = View.VISIBLE
                txt_field_verification_code.visibility = View.VISIBLE
                btn_verify.visibility = View.VISIBLE
                resend_link.visibility = View.VISIBLE
                Toast.makeText(this@RegisterActivity,"Verification Code sent",Toast.LENGTH_SHORT).show()
            }
        }
        // [END phone_auth_callbacks]

        login_link = findViewById(R.id.login_link)
        login_link.setOnClickListener(View.OnClickListener {
            val intent = Intent (this,LoginActivity::class.java).apply{

            }
            startActivity(intent)
        })

        // Verify button: validate, verify phone number with verification code
        btn_verify.setOnClickListener(View.OnClickListener {
            var code :String = et_verification_code.getText().toString()

            if (TextUtils.isEmpty(code)) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else{
                verifyPhoneNumberWithCode(storedVerificationId,code)
                et_verification_code.visibility = View.VISIBLE
            }
        })

        btn_continue = findViewById<Button>(R.id.btn_continue)
        btn_continue.setOnClickListener(View.OnClickListener {
            var contactno : String = et_contact_no.getText().toString()


            if (TextUtils.isEmpty(contactno)) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else{
                startPhoneNumberVerification(contactno)

            }
        })

        //resend OTP button: if code didnt receive, resend OTP
        resend_link.setOnClickListener {
            var contactno : String = et_contact_no.getText().toString()

            if (TextUtils.isEmpty(contactno)) {
                // Check if all fields are filled
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else{
                resendVerificationCode(contactno,resendToken)
            }
        }
        if (verification == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    private fun startPhoneNumberVerification(phoneNumber: String) {
        progressDialog.setMessage("Verifying Phone Number")
        progressDialog.show()
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
    }

    //resend verification code
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?) {
        progressDialog.setMessage("Resending Code")
        progressDialog.show()
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    // verify phone number with code
    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        progressDialog.setMessage("Verifying Code")
        progressDialog.show()
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signUpWithPhoneAuthCredential(credential)
    }

    // sign in with phone
    private fun signUpWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setMessage("SignUp")

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // login success
                    progressDialog.dismiss()
                    val phone = auth.currentUser?.phoneNumber
                    Toast.makeText(this, "Sign Up Success",Toast.LENGTH_SHORT).show()

                    //start register activity 2
                    startActivity(Intent(this,RegisterActivity2::class.java))
                } else {
                    // login failed
                    Toast.makeText(this,"Retry again",Toast.LENGTH_SHORT).show()
                }
            }
    }
    // [END sign_in_with_phone]

    // over-ride these 2 methods
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(storedVerificationId, verification)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        verification = savedInstanceState.getString(storedVerificationId)
    }

    private fun updateUI(user: FirebaseUser? = auth.currentUser) {

    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }


    override fun onResume() {
        super.onResume()
    }
}