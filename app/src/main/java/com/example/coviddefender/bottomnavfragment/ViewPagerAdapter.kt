package com.example.coviddefender.bottomnavfragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coviddefender.bottomnavfragment.ThingsToKnow
import com.example.coviddefender.bottomnavfragment.ThingsToDo

class ViewPagerAdapter(fa: FragmentInfo) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                ThingsToKnow.newInstance()
            }
            1 -> {
                ThingsToDo.newInstance()
            }
            else -> ThingsToKnow.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}