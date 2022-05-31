package com.example.coviddefender.bottomnavfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coviddefender.R
import com.example.coviddefender.bottomnavfragment.ThingsToKnow
import com.example.coviddefender.bottomnavfragment.ThingsToDo
import com.example.coviddefender.recyclerview.*
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


//Things To Do Recycler View
        // Dummy data for recycler view
        var todo: ArrayList<ToDo> = arrayListOf(
            ToDo(
                R.drawable.covid_illustration,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            ToDo(
                R.drawable.myths_about_covid_vaccine,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            ToDo(
                R.drawable.father_and_son,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            )
        )
        // Recycler View
        val todo_recyclerview: RecyclerView? =
            view.findViewById<RecyclerView>(R.id.todo_recyclerview)
        todo_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        // Adopt data to recycler view using adapter
        todo_recyclerview?.adapter = ToDoAdapter(todo)

//Things To Do Recycler View
        // Dummy data for recycler view
        var toknow: ArrayList<ToKnow> = arrayListOf(
            ToKnow(
                R.drawable.covid_illustration,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            ToKnow(
                R.drawable.myths_about_covid_vaccine,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            ),
            ToKnow(
                R.drawable.father_and_son,
                "Lorem ipsum dolor sit amet, consectetur adipiscin"
            )
        )
        // Recycler View
        val toknow_recyclerview: RecyclerView? =
            view.findViewById<RecyclerView>(R.id.toknow_recyclerview)
        todo_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        // Adopt data to recycler view using adapter
        toknow_recyclerview?.adapter = ToKnowAdapter(toknow)

        // back button
        val info_back : ImageButton = view.findViewById<ImageButton>(R.id.info_back)
        info_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_info_to_home)

        })


        return view

    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentInfo()
    }
}