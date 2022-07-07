package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.HistoryListAdapter
import com.example.coviddefender.entity.History
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HistoryFragmenet : Fragment() {
    private lateinit var history_recyclerview: RecyclerView
    private lateinit var pb_loading: ProgressBar
    private lateinit var historyListAdapter: HistoryListAdapter

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        // Set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
        }
        userId = currentUser.uid

        // set up firestore storage
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore.collection("history").document("testing")

        // Initialize variables
        history_recyclerview =
            view.findViewById<RecyclerView>(R.id.history_recyclerview)
        pb_loading = view.findViewById(R.id.pb_loading)
        pb_loading.visibility = View.VISIBLE

        // set up recycler view
        setUpRecyclerView()

        // back button
        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_history_to_home)

        })
        return view

    }

    private fun setUpRecyclerView() {
        var query:Query = docRef.collection("historyItem").orderBy("time", Query.Direction.DESCENDING)

        var options: FirestoreRecyclerOptions<History> = FirestoreRecyclerOptions.Builder<History>()
            .setQuery(query,History::class.java)
            .build()

        historyListAdapter = HistoryListAdapter(options)

        // setting layout malinger to recycler view
        history_recyclerview.layoutManager = LinearLayoutManager(
            view?.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        history_recyclerview.adapter = historyListAdapter
        pb_loading.visibility = View.GONE

    }

    override fun onStart() {
        super.onStart()
        historyListAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        historyListAdapter.stopListening()
    }


    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragmenet()
    }
}