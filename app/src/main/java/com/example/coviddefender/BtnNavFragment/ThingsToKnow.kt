package com.example.coviddefender.BtnNavFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.db.toknow.ToKnow
import com.example.coviddefender.RecyclerViewAdapter.ToKnowAdapter

class ThingsToKnow : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_things_to_know, container, false)

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
        toknow_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        // Adopt data to recycler view using adapter
        toknow_recyclerview?.adapter = ToKnowAdapter(toknow)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThingsToKnow()
    }
}