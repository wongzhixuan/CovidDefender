package com.example.coviddefender.NavDrawerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class EditProfile : Fragment() {

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference

    // Storage
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference

    lateinit var tv_profile_name : TextView
    lateinit var tv_profile_id: TextView
    lateinit var profile_image : ShapeableImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        tv_profile_id = view.findViewById(R.id.tv_profile_id)
        tv_profile_name = view.findViewById(R.id.tv_profile_name)
        profile_image = view.findViewById(R.id.profile_image)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.getCurrentUser()!!
        userId = currentUser!!.getUid()

        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore!!.collection("users").document(userId!!)

        // set up firebase storage
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()
        storageReference.child("users/" + mAuth!!.currentUser!!.uid + "/profile.jpg")

        // get data from firebase
        setUpTextView()


        val btn_profile_details: MaterialButton =
            view.findViewById<MaterialButton>(R.id.btn_profile_details)
        btn_profile_details.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editprofile_to_editdetails)

        })

        val btn_profile_change_password: MaterialButton =
            view.findViewById<MaterialButton>(R.id.btn_profile_change_password)
        btn_profile_change_password.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editprofile_to_changepassword)

        })

        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_editprofile_to_home)

        })
        return view
    }


    private fun setUpTextView() {
        var name: String? = currentUser.displayName
        tv_profile_name.text = name
        var id: String = ""
        var docRefUsers: DocumentReference = firestore.collection("users").document(currentUser.uid)
        docRefUsers.get().addOnSuccessListener { document ->
            id = document.get("nric").toString()
            tv_profile_id.text = id
        }
        var photoUrl = currentUser.photoUrl
        if(photoUrl!=null){
            Picasso.get().load(photoUrl).into(profile_image)
        }
    }

        companion object {
            @JvmStatic
            fun newInstance() = EditProfile()
        }
    }
