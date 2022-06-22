package com.example.coviddefender.BtnNavFragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter2(fa: FragmentStatistics) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                DailyUpdates.newInstance()
            }
            1 -> {
                Global.newInstance()
            }
            else -> DailyUpdates.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}