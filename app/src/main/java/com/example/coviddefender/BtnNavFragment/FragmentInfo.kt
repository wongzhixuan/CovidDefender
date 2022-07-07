package com.example.coviddefender.BtnNavFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.AnnouncementAdapter
import com.example.coviddefender.entity.Announcement
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class FragmentInfo : Fragment() {

    private var pagerAdapter: FragmentStateAdapter? = null
    lateinit var announcement_recyclerview: RecyclerView
    lateinit var announcementAdapter: AnnouncementAdapter


    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_info, container, false)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
//            userId = currentUser.uid
            userId = "testing"
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()

        val tab_layout = view.findViewById<TabLayout>(R.id.info_tab)
        val viewpager = view.findViewById<ViewPager2>(R.id.viewpager2)

        // Adapt the viewpager using the ViewPagerAdapter
        pagerAdapter = ViewPagerAdapter(this)
        viewpager.setAdapter(pagerAdapter)

        val titles = arrayOf("Things To Know", "Things To Do")
        //displaying tabs and mediate the TabLayout with the ViewPager2
        TabLayoutMediator(
            tab_layout,
            viewpager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()

        // Announcement Recycler View
        announcement_recyclerview =
            view.findViewById<RecyclerView>(R.id.latest_announcement_recyclerview)

        // set up announcement recycler view with data from firebase
        setUpRecyclerView()

        return view
    }

    private fun setUpRecyclerView() {
        // set query for firestore
        var query: Query =
            firestore.collection("announcements").orderBy("description", Query.Direction.ASCENDING)
        var options: FirestoreRecyclerOptions<Announcement> =
            FirestoreRecyclerOptions.Builder<Announcement>()
                .setQuery(query, Announcement::class.java)
                .build()

        announcementAdapter = AnnouncementAdapter(options)

        announcement_recyclerview?.layoutManager = LinearLayoutManager(
            view?.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // Adopt data to recycler view using adapter
        announcement_recyclerview.adapter = announcementAdapter

    }

    override fun onStart() {
        super.onStart()
        announcementAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        announcementAdapter.stopListening()
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentInfo()
    }
}