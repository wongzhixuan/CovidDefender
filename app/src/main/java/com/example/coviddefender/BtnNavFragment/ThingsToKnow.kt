package com.example.coviddefender.BtnNavFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.AnnouncementAdapter
import com.example.coviddefender.RecyclerViewAdapter.ThingsToKnowAdapter
import com.example.coviddefender.entity.Announcement
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ThingsToKnow : Fragment() {

    lateinit var thinsgtoknow_recyclerview : RecyclerView
    lateinit var thingsToKnowAdapter: ThingsToKnowAdapter

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference
    private lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_things_to_know, container, false)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
//            userId = currentUser.uid
            userId = "testing"
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()

        // Recycler View
        val toknow_recyclerview: RecyclerView? =
            view.findViewById<RecyclerView>(R.id.toknow_recyclerview)
        toknow_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // To Know Recycler View
        thinsgtoknow_recyclerview=
            view.findViewById<RecyclerView>(R.id.toknow_recyclerview)

        setUpRecyclerView()
        return view
    }

    private fun setUpRecyclerView() {
        var query: Query = firestore.collection("to_know").orderBy("description", Query.Direction.ASCENDING)
        var options: FirestoreRecyclerOptions<Announcement> = FirestoreRecyclerOptions.Builder<Announcement>()
            .setQuery(query, Announcement::class.java)
            .build()

        thingsToKnowAdapter = ThingsToKnowAdapter(options)

        thinsgtoknow_recyclerview?.layoutManager = LinearLayoutManager(
            view?.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        // Adopt data to recycler view using adapter
        thinsgtoknow_recyclerview.adapter = thingsToKnowAdapter

    }

    override fun onStart() {
        super.onStart()
        thingsToKnowAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        thingsToKnowAdapter.stopListening()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThingsToKnow()
    }
}