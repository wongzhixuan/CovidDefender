package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.HistoryListAdapter
import com.example.coviddefender.db.history.HistoryModal
import com.example.coviddefender.db.history.HistoryViewModel
import com.example.coviddefender.entity.History
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class HistoryFragmenet : Fragment() {
    private lateinit var history_recyclerview: RecyclerView
    private lateinit var pb_loading: ProgressBar
    private lateinit var historyListAdapter: HistoryListAdapter
    private lateinit var historyList: ArrayList<HistoryModal>

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
        historyList = arrayListOf()

        // setting layout malinger to recycler view
        history_recyclerview.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        // initialize adapter
        historyListAdapter = HistoryListAdapter(historyList, context, this::onHistoryClick)
        // setting adapter to recycler view
        history_recyclerview.adapter = historyListAdapter
        // get data from firebase
        getAllData()

        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_history_to_home)

        })
        return view
    }

    private fun getAllData() {
        // clear list
        historyList.clear()

        docRef.collection("historyItem").get()
            .addOnCompleteListener(OnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    var querySnapshot: QuerySnapshot? = task.result
                    querySnapshot?.forEach { queryDocumentSnapshot ->

                        Log.d("HistoryList", queryDocumentSnapshot.data.toString())
                    }
                } else {
                    Log.d("HistoryList", task.exception.toString())
                }
            })
            .addOnFailureListener { exception ->
                Log.d("HistoryList", exception.message.toString())
            }
    }

    @Override
    public fun onHistoryClick(position:Int){

    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragmenet()
    }
}