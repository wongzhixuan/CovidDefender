package com.example.coviddefender.BtnNavFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.entity.ToDo
import com.example.coviddefender.RecyclerViewAdapter.ToDoAdapter

class ThingsToDo : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_things_to_do, container, false)
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

        //To Do Recycler View
        val todo_recyclerview: RecyclerView? =
           view.findViewById<RecyclerView>(R.id.todo_recyclerview)
        todo_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // Adopt data to recycler view using adapter
        todo_recyclerview?.adapter = ToDoAdapter(todo)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThingsToDo()
    }
}