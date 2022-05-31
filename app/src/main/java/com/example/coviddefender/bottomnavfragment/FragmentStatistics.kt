package com.example.coviddefender.bottomnavfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coviddefender.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentStatistics : Fragment() {

    private var pagerAdapter: FragmentStateAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View = inflater.inflate(R.layout.fragment_statistics, container, false)

        val tab_layout = view.findViewById<TabLayout>(R.id.info_tab)
        val statistics_viewpager = view.findViewById<ViewPager2>(R.id.statistics_viewpager)

        // Adapt the viewpager using the ViewPagerAdapter
        pagerAdapter = ViewPagerAdapter2(this)
        statistics_viewpager.setAdapter(pagerAdapter)

        val titles = arrayOf("Daily Updates", "Covid-19 State", "Global")
        //displaying tabs and mediate the TabLayout with the ViewPager2
        TabLayoutMediator(
            tab_layout,
            statistics_viewpager
        ) {
                tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()

        // back button
        val statistics_back : ImageButton = view.findViewById<ImageButton>(R.id.statistics_back)
        statistics_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_statistics_to_home)

        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentStatistics()
    }
}