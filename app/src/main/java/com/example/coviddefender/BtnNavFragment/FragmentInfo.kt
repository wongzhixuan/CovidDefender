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
import com.example.coviddefender.entity.Announcement
import com.example.coviddefender.RecyclerViewAdapter.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FragmentInfo : Fragment() {

    private var pagerAdapter: FragmentStateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_info, container, false)

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
        ) {
                tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()

// Announcement Recycler View
        // Dummy data for recycler view
        var announcements: ArrayList<Announcement> = arrayListOf(
            /*Announcement(
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
            )*/
        )
        //Recycler View
        val announcement_recyclerview: RecyclerView? =
            view.findViewById<RecyclerView>(R.id.latest_announcement_recyclerview)
        announcement_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // Adopt data to recycler view using adapter
        announcement_recyclerview?.adapter = AnnouncementAdapter(announcements)

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentInfo()
    }
}