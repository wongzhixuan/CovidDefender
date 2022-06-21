package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.db.history.History
import com.example.coviddefender.db.history.HistoryViewModel
import com.example.coviddefender.RecyclerViewAdapter.HistoryListAdapter


class HistoryFragmenet : Fragment() {
    private lateinit var historyViewModel: HistoryViewModel
    private final var historyList : ArrayList<History> = arrayListOf()
    private lateinit var history_recyclerview: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)

        // History Recycler View
        history_recyclerview  =
            view.findViewById<RecyclerView>(R.id.history_recyclerview)
        history_recyclerview.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        var adapter:HistoryListAdapter = HistoryListAdapter(HistoryListAdapter.HistoryDiff())
        // adopt data to recycler view using adapter
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        historyViewModel.allHistory.observe(
            viewLifecycleOwner
        ) { historyList -> adapter.submitList(historyList) }


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