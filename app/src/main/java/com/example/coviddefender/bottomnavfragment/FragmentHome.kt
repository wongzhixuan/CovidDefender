package com.example.coviddefender.bottomnavfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.recyclerview.Announcement
import com.example.coviddefender.recyclerview.AnnouncementAdapter


class FragmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        // Dummy data for recycler view
        var announcements: ArrayList<Announcement> = arrayListOf(
            Announcement(
                R.drawable.covid_illustration,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            Announcement(
                R.drawable.myths_about_covid_vaccine,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            Announcement(
                R.drawable.father_and_son,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            )
            )

        // Announcement Recycler View
        val announcement_recyclerview: RecyclerView? = view?.findViewById<RecyclerView>(R.id.latest_announcement_recyclerview)
        announcement_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // Adopt data to recycler view using adapter
        announcement_recyclerview?.adapter = AnnouncementAdapter(announcements)

        return view

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentHome()
    }
}