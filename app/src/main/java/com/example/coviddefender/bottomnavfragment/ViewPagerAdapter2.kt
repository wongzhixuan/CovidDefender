package com.example.coviddefender.bottomnavfragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coviddefender.bottomnavfragment.ThingsToKnow
import com.example.coviddefender.bottomnavfragment.ThingsToDo

class ViewPagerAdapter2(fa: FragmentStatistics) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                DailyUpdates.newInstance()
            }
            1 -> {
                Covid19State.newInstance()
            }
            2 -> {
                Global.newInstance()
            }
            else -> DailyUpdates.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}