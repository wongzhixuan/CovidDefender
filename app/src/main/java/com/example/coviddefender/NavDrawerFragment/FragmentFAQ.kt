package com.example.coviddefender.NavDrawerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.FAQAdapter
import com.example.coviddefender.entity.FAQ
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentFAQ : Fragment() {

    val db = Firebase.firestore
    lateinit var faqAdapter: FAQAdapter
    lateinit var faq_recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_faq, container, false)


        // FAQ Recycler View
        faq_recyclerview =
            view.findViewById<RecyclerView>(R.id.faq_recyclerview)

        // set up recycler view
        setUpRecyclerView()

        val FAQ_back: ImageButton = view.findViewById<ImageButton>(R.id.FAQ_back)
        FAQ_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_faq_to_home)

        })
        return view
    }

    override fun onStart() {
        super.onStart()
        faqAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        faqAdapter.stopListening()
    }

    private fun setUpRecyclerView() {
        var query: Query =
            db.collection("faq")
        var options: FirestoreRecyclerOptions<FAQ> =
            FirestoreRecyclerOptions.Builder<FAQ>()
                .setQuery(query, FAQ::class.java)
                .build()

        faqAdapter = FAQAdapter(options)
        faq_recyclerview?.layoutManager = LinearLayoutManager(
            view?.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        faq_recyclerview.adapter = faqAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentFAQ()
    }
}