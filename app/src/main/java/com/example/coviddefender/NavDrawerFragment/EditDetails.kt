package com.example.coviddefender.NavDrawerFragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditDetails : Fragment() {
    private lateinit var tv_profile_name: TextView
    private lateinit var tv_profile_id: TextView

    // Firebase Authentication
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    var userId: String? = null

    // Firestore
    lateinit var firestore: FirebaseFirestore
    lateinit var docRef: DocumentReference

    // Storage
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var et_NRIC: TextInputEditText
    lateinit var et_full_name: TextInputEditText
    lateinit var et_mail: TextInputEditText
    lateinit var txt_field_email: TextInputLayout
    lateinit var profile_image: ShapeableImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_edit_details, container, false)

        et_full_name = view.findViewById(R.id.et_full_name)
        et_NRIC = view.findViewById(R.id.et_NRIC)
        et_mail = view.findViewById(R.id.et_mail)
        txt_field_email = view.findViewById(R.id.txt_field_email)
        profile_image = view.findViewById(R.id.profile_image)
        tv_profile_name = view.findViewById(R.id.tv_profile_name)
        tv_profile_id = view.findViewById(R.id.tv_profile_id)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.getCurrentUser()
        userId = currentUser!!.getUid()

        // if user not null
        if (currentUser != null) {

            // set up firestore
            firestore = FirebaseFirestore.getInstance()
            docRef = firestore!!.collection("users").document(userId!!)

            // set up firebase storage
            storage = FirebaseStorage.getInstance()
            storageReference = storage.getReference()
            storageReference.child("users/" + mAuth!!.currentUser!!.uid + "/profile.jpg")

            // get data from firebase
            setUpViewWithData()
        }

        et_NRIC.isEnabled = false
        et_full_name.isEnabled = false

        profile_image.setOnClickListener{

        }

        val btn_save: Button = view.findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener(View.OnClickListener {
            updateFirebase()
            findNavController().navigate(R.id.action_editdetails_to_editprofile)

        })

        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editdetails_to_editprofile)

        })
        return view
    }

    private fun setUpViewWithData() {
        // get data from firebase auth
        var name: String? = currentUser!!.displayName
        tv_profile_name.text = name
        var id: String = ""
        var docRefUsers: DocumentReference = firestore.collection("users").document(currentUser!!.uid)
        docRefUsers.get().addOnSuccessListener { document ->
            id = document.get("nric").toString()
            tv_profile_id.text = id
            et_NRIC.setText(id)
        }
        var photoUrl = currentUser!!.photoUrl
        if(photoUrl!=null){
            Picasso.get().load(photoUrl).into(profile_image)
        }
        val username = currentUser!!.displayName
        if (username != null) {
            et_full_name.setText(username)
        }
        val email = currentUser!!.email
        if (email != null) {
            et_mail.setText(email)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = EditDetails()
    }

    private fun selectImageFromGallery() {
        //open gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                val imageUri = data!!.data
                uploadToFirebase(imageUri!!)
            }
        }
    }

    private fun updateFirebase() {
        if (validateInput()!!) {
            val name = et_full_name.getText().toString().trim { it <= ' ' }
            val email = et_mail.getText().toString().trim { it <= ' ' }
            val nric = et_NRIC.getText().toString().trim { it <= ' ' }
            val changeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            currentUser!!.updateProfile(changeRequest)
            val userDetails: MutableMap<String, Any> = HashMap()
            userDetails["fullName"] = name
            userDetails["nric"] = nric
            userDetails["emailAdd"] = email
            docRef.update(userDetails).addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Profile Updated",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Error! " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadToFirebase(imageUri: Uri) {
        // upload image to Firebase storage
        val fileRef = storageReference.child("users/" + mAuth!!.currentUser!!.uid + "/profile.jpg")
        fileRef.putFile(imageUri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build()
                currentUser!!.updateProfile(changeRequest).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show()
                        val photoUrl = currentUser!!.photoUrl
                        Picasso.get().load(photoUrl).into(profile_image)
                    }
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(context, "Error! " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAllData() {
        // get data from firebase auth
//        val username = currentUser!!.displayName
//        if (username != null) {
//            et_full_name.setText(username)
//        }
//        val email = currentUser!!.email
//        if (email != null) {
//            et_mail.setText(email)
//        }
//        if (currentUser!!.photoUrl != null) {
//            val photoUrl = currentUser!!.photoUrl
//            Picasso.get().load(photoUrl).into(profile_image)
//        }
//        // get data from firestore
//        docRef.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val document = task.result
//                if (document.exists()) {
//                    document.getString("fullname")
//                    document.getString("nric")
//                    document.getString("emailAdd")
//                } else {
//                    Log.d("Firestore", "No such document")
//                }
//            } else {
//                Log.d("Firestore", "get failed with ", task.exception)
//            }
//        }
    }

    private fun validateInput(): Boolean? {
        txt_field_email.setError(null)
        if (et_mail.getText().toString().trim { it <= ' ' } == "") {
            txt_field_email.setError("This field cannot be empty")
            txt_field_email.requestFocus()
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getAllData()
    }

    override fun onStart() {
        super.onStart()
        getAllData()
    }
}