package com.example.coviddefender.homefragmentsubpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.recyclerview.History
import com.example.coviddefender.recyclerview.HistoryAdapter


class HistoryFragmenet : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)

        // Dummy data for recycler view
        var historys: ArrayList<History> = arrayListOf(
            History(
                R.drawable.ic_hotspot,
                "Xiamen University Malaysia",
                "Apr 4, 2022, 10:22 AM",
                false
            ),
            History(R.drawable.ic_hotspot, "Random Location 1", "Apr 1, 2022, 10:22 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 123456", "Apr 1, 2022, 9:30 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 654321", "Apr 1, 2022, 9:22 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 1", "Apr 1, 2022, 10:22 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 123456", "Apr 1, 2022, 9:30 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 654321", "Apr 1, 2022, 9:22 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 1", "Apr 1, 2022, 10:22 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 123456", "Apr 1, 2022, 9:30 AM", true),
            History(R.drawable.ic_hotspot, "Random Location 654321", "Apr 1, 2022, 9:22 AM", true)

        )
        // History Recycler View
        val history_recyclerview: RecyclerView =
            view.findViewById<RecyclerView>(R.id.history_recyclerview)
        history_recyclerview.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        // adopt data to recycler view using adapter
        history_recyclerview.adapter = HistoryAdapter(historys)



        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_history_to_home)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragmenet()
    }
}