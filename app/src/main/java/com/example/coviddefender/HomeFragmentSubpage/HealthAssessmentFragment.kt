package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.QuestionAdapter
import com.example.coviddefender.entity.Question
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HealthAssessmentFragment : Fragment() {
    lateinit var tv_question_title: TextView
    lateinit var btn_answer_no: MaterialButton
    lateinit var btn_answer_yes: MaterialButton
    lateinit var btn_submit: MaterialButton
    lateinit var progress_bar: ProgressBar
    lateinit var btn_back: ImageButton

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String

    // Firestore
    private lateinit var firestore: FirebaseFirestore

    // array list of questions
    private lateinit var questions: ArrayList<Question>

    // current question id
    private var questionId: Int = 1


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
        btn_back = view.findViewById<ImageButton>(R.id.btn_back)
        progress_bar = view.findViewById(R.id.progress_bar)
        tv_question_title = view.findViewById(R.id.tv_question_title)
        btn_answer_no = view.findViewById(R.id.btn_answer_no)
        btn_answer_yes = view.findViewById(R.id.btn_answer_yes)
        btn_submit = view.findViewById(R.id.btn_submit)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
//            userId = currentUser.uid
            userId = "testing"
        }

        // set up firestore
        firestore = FirebaseFirestore.getInstance()

        // back button
        btn_back?.setOnClickListener{
            findNavController().navigate(R.id.action_health_assessment_to_home)

        }

        // reset buttons
        resetButtons()

        // get question data
        questions = arrayListOf()
        getQuestionData()

        // set up text view
        setUpQuestionView()

        btn_answer_yes.setOnClickListener {
            btn_answer_yes.isChecked = true
            btn_answer_no.isChecked = false
            btn_answer_yes.setBackgroundResource(R.color.light_orange)
            btn_answer_no.setBackgroundResource(R.color.light_coral)
        }

        btn_answer_no.setOnClickListener {
            btn_answer_no.isChecked = true
            btn_answer_yes.isChecked = false
            btn_answer_yes.setBackgroundResource(R.color.light_green)
            btn_answer_no.setBackgroundResource(R.color.light_orange)
        }

        btn_submit.setOnClickListener {
            if(btn_answer_yes.isChecked && !btn_answer_no.isChecked){

                questionId += 1
            }
            else if (btn_answer_no.isChecked && !btn_answer_yes.isChecked){
                questionId += 1
            }
            else{
                Toast.makeText(context, "Please select an answer",Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun resetButtons() {
        btn_answer_yes.isChecked = false
        btn_answer_no.isChecked = false
        btn_answer_yes.setBackgroundResource(R.color.light_green)
        btn_answer_no.setBackgroundResource(R.color.light_coral)
    }

    private fun setUpQuestionView() {
        var current_question: Question = questions[questionId]
        var current_question_title: String = current_question.question
        tv_question_title.text = current_question_title
    }

    private fun getQuestionData() {
        firestore.collection("health_assessment").orderBy("id",Query.Direction.ASCENDING).get()
            .addOnSuccessListener { documents->
                for(document in documents){
                    var question: Question = document.toObject(Question::class.java)
                    questions.add(question)
                }
            }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HealthAssessmentFragment()
    }
}