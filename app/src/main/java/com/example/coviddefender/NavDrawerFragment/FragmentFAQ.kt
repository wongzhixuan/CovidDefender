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
import com.example.coviddefender.entity.FAQ
import com.example.coviddefender.RecyclerViewAdapter.FAQAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentFAQ : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view :View =  inflater.inflate(R.layout.fragment_faq, container, false)

        // Dummy data for recycler view
        var faq: ArrayList<FAQ> = arrayListOf(
            FAQ(
               db.collection("faq").document("testing").get().toString(),
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            FAQ(
                "FAQ 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            FAQ(
                "FAQ 3",
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            )
        )

        // FAQ Recycler View
        val faq_recyclerview: RecyclerView? =
            view.findViewById<RecyclerView>(R.id.faq_recyclerview)
        faq_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // Adopt data to recycler view using adapter
        faq_recyclerview?.adapter = FAQAdapter(faq)


        val FAQ_back : ImageButton = view.findViewById<ImageButton>(R.id.FAQ_back)
        FAQ_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_faq_to_home)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentFAQ()
    }
}