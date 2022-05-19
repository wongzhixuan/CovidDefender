package com.example.coviddefender.homefragmentsubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.recyclerview.Dependent
import com.example.coviddefender.recyclerview.DependentAdapter
import com.example.coviddefender.recyclerview.HistoryAdapter

class GroupCheckIn : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_group_check_in, container, false)

        // Dummy data for recycler view
        var dependents: ArrayList<Dependent> = arrayListOf(
            Dependent("Cloud Strife", "Friend", true),
            Dependent("Cloud Strife", "Friend", false),
            Dependent("Cloud Strife", "Friend", false),
            Dependent("Cloud Strife", "Friend", false),
            Dependent("Cloud Strife", "Friend", false),
            Dependent("Cloud Strife", "Friend", false),
            Dependent("Cloud Strife", "Friend", false),
            Dependent("Cloud Strife", "Friend", false),

        )
        // Dependent Recycler View
        val dependent_recyclerview: RecyclerView = view.findViewById<RecyclerView>(R.id.dependent_recyclerview)
        dependent_recyclerview.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // adopt data to recycler view using adapter
        dependent_recyclerview.adapter = DependentAdapter(dependents)


        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_groupCheckIn_to_home)

        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = GroupCheckIn()
    }
}