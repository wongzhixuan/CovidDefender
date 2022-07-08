package com.example.coviddefender.BtnNavFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.AnnouncementAdapter
import com.example.coviddefender.UserAuthentication.LoginActivity
import com.example.coviddefender.entity.Announcement
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso


class FragmentHome : Fragment() {
    var card_covid_status: MaterialCardView? = null
    var card_health_assessment: MaterialCardView? = null
    var card_hotspot: MaterialCardView? = null

    var card_checkin: MaterialCardView? = null
    var card_history: MaterialCardView? = null
//    var card_group: MaterialCardView? = null

    var card_vaccine_status: MaterialCardView? = null
    var card_appointment: MaterialCardView? = null

    lateinit var drawer_layout: DrawerLayout
    lateinit var drawer_nav_view: NavigationView
    lateinit var btn_drawer: ImageButton
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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
//            userId = currentUser.uid
            userId = "testing"
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()

        // Drawer
        drawer_layout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer_nav_view = view.findViewById<NavigationView>(R.id.nav_view_drawer)
        btn_drawer = view.findViewById<ImageButton>(R.id.btn_nav_drawer)
        drawer_nav_view.setNavigationItemSelectedListener(drawerListener)

        btn_drawer.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        // link drawer widgets
        var header : View = drawer_nav_view.getHeaderView(0)
        var profile_image = header.findViewById<ShapeableImageView>(R.id.profile_image)
        var tv_profile_name = header.findViewById<TextView>(R.id.tv_profile_name)
        var tv_profile_id = header.findViewById<TextView>(R.id.tv_profile_id)

        // get data from firebase auth and firestore to set up drawer header
        tv_profile_name.setText(currentUser.displayName)
        if(currentUser.photoUrl != null){
            var photoUrl = currentUser.photoUrl
            Picasso.get().load(photoUrl).into(profile_image)
        }
        var id: String = ""
        var docRefUsers: DocumentReference = firestore.collection("users").document(currentUser!!.uid)
        docRefUsers.get().addOnSuccessListener { document ->
            id = document.get("nric").toString()
            tv_profile_id.text = id
        }

        // Announcement Recycler View
        announcement_recyclerview =
            view.findViewById<RecyclerView>(R.id.latest_announcement_recyclerview)

        // set up recycler view with data from firebase
        setUpRecyclerView()


        // Link card view widgets
        card_covid_status = view.findViewById<MaterialCardView>(R.id.card_covid_status)
        card_health_assessment = view.findViewById<MaterialCardView>(R.id.card_health_assessment)
        card_hotspot = view.findViewById<MaterialCardView>(R.id.card_hotspot)

        card_checkin = view.findViewById<MaterialCardView>(R.id.card_checkin)
        card_history = view.findViewById<MaterialCardView>(R.id.card_history)
        card_vaccine_status = view.findViewById<MaterialCardView>(R.id.card_vaccine_status)
        card_appointment = view.findViewById<MaterialCardView>(R.id.card_appointment)

        card_covid_status?.setOnClickListener {
            findNavController().navigate(R.id.covid_status)
        }
        card_health_assessment?.setOnClickListener {
            findNavController().navigate(R.id.health_assessment)
        }
        card_hotspot?.setOnClickListener {
            findNavController().navigate(R.id.hotspot)
        }
        card_checkin?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_checkIn)
        }
        card_history?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_history)
        }
//        card_group?.setOnClickListener {
//            findNavController().navigate(R.id.action_home_to_groupCheckIn)
//        }
        card_vaccine_status?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_vaccineStatus)
        }
        card_appointment?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_appointment)
        }

        return view
    }

    private fun setUpRecyclerView() {
        // set up recycler view with firebase firestore ui recyclerview adapter
        var query: Query =
            firestore.collection("announcements").orderBy("description", Query.Direction.ASCENDING)
        var options: FirestoreRecyclerOptions<Announcement> =
            FirestoreRecyclerOptions.Builder<Announcement>()
                .setQuery(query, Announcement::class.java)
                .build()

        announcementAdapter = AnnouncementAdapter(options)

        // set linear layout manager
        announcement_recyclerview?.layoutManager = LinearLayoutManager(
            view?.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // set adapter
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
    // drawer listener
    private val drawerListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.faq -> {
                    findNavController().navigate(R.id.faq)
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.profile)
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
                R.id.settings -> {
                    findNavController().navigate(R.id.settings)
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
                R.id.log_out -> {
                    mAuth.signOut()
                    val intent: Intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
                else -> {
                    false
                }
            }
            true
        }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentHome()
    }
}

