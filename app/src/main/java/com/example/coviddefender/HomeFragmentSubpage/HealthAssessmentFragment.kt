package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.entity.Question
import com.example.coviddefender.RecyclerViewAdapter.QuestionAdapter


class HealthAssessmentFragment : Fragment() {
    lateinit var question_recyclerview: RecyclerView
    lateinit var progress_bar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_health_assessment, container, false)
        // link widgets
        val btn_back : ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        question_recyclerview =
            view.findViewById<RecyclerView>(R.id.questions_recyclerview)
        progress_bar = view.findViewById(R.id.progress_bar)

        btn_back?.setOnClickListener{
            findNavController().navigate(R.id.action_health_assessment_to_home)

        }

        // Dummy data for recycler view
        var questions: ArrayList<Question> = arrayListOf(
            Question(
                "Are you exhibiting 2 or more symptoms as listed below:\n• Fever\n• Chills\n• Body ache\n• Headache\n• Sore throat\n• Diarrhea\n• Vomitting \n• Fatigue\n• Nasal congestion"
            ),
            Question(
                "Are you exhibiting 2 or more symptoms as listed below:\n• Fever\n• Chills\n• Body ache\n• Headache\n• Sore throat\n• Diarrhea\n• Vomitting \n• Fatigue\n• Nasal congestion"
            ),
            Question(
                "Are you exhibiting 2 or more symptoms as listed below:\n• Fever\n• Chills\n• Body ache\n• Headache\n• Sore throat\n• Diarrhea\n• Vomitting \n• Fatigue\n• Nasal congestion"
            ),
            Question(
                "Are you exhibiting 2 or more symptoms as listed below:\n• Fever\n• Chills\n• Body ache\n• Headache\n• Sore throat\n• Diarrhea\n• Vomitting \n• Fatigue\n• Nasal congestion"
            ),
            Question(
                "Are you exhibiting 2 or more symptoms as listed below:\n• Fever\n• Chills\n• Body ache\n• Headache\n• Sore throat\n• Diarrhea\n• Vomitting \n• Fatigue\n• Nasal congestion"
            )
        )

        // Question Recycler View
        question_recyclerview?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        // Adopt data to recycler view using adapter
        question_recyclerview?.adapter = QuestionAdapter(questions)
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = HealthAssessmentFragment()
    }
}