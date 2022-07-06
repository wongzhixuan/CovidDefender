package com.example.coviddefender.HomeFragmentSubpage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.example.coviddefender.entity.Question
import com.google.android.material.button.MaterialButton
import com.google.common.io.Resources
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

    public lateinit var answers: Array<String>


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
        questions = getQuestionData()

        // set up text view
        setUpQuestionView(questions, questionId)

        answers = arrayOf()

        btn_answer_yes.setOnClickListener {
            btn_answer_yes.isChecked = true
            btn_answer_no.isChecked = false
            btn_answer_yes.setStrokeColorResource(R.color.light_orange)
            btn_answer_no.setStrokeColorResource(R.color.black)
        }

        btn_answer_no.setOnClickListener {
            btn_answer_no.isChecked = true
            btn_answer_yes.isChecked = false
            btn_answer_yes.setStrokeColorResource(R.color.black)
            btn_answer_no.setStrokeColorResource(R.color.light_orange)
        }

        btn_submit.setOnClickListener {
            if(btn_answer_yes.isChecked && !btn_answer_no.isChecked){
                answers[questionId-1] = "Yes"
                questionId += 1
                setUpQuestionView(questions, questionId)
            }
            else if (btn_answer_no.isChecked && !btn_answer_yes.isChecked){
                answers[questionId-1] = "No"
                questionId += 1
                setUpQuestionView(questions, questionId)
            }
            else{
                Toast.makeText(context, "Please select an answer",Toast.LENGTH_SHORT).show()
            }
            if(questionId == questions.size){
                var num_no = 0
                var num_yes = 0
                for(answer in answers){
                    when(answer){
                        "No" -> num_no += 1
                        "Yes" -> num_yes += 1
                    }
                }
                var docRef: DocumentReference = firestore.collection("covid_status").document(userId)
                if(num_no < num_yes){
                    docRef.update("covid_status", "High Risk").addOnSuccessListener {
                    }
                }
                else{
                    docRef.update("covid_status","Low Risk").addOnSuccessListener{

                    }

                }
                var docRefRecords: DocumentReference = firestore.collection("health_assessment_records").document(userId)
                var data = hashMapOf(
                    "answer" to answers
                )
                docRefRecords.collection("records").add(data).addOnSuccessListener {
                    Toast.makeText(context,"Successfully Completed Health Assessment",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.home)
                }
            }
        }

        return view
    }

    private fun resetButtons() {
        btn_answer_yes.isChecked = false
        btn_answer_no.isChecked = false
        btn_answer_yes.setStrokeColorResource(R.color.black)
        btn_answer_no.setStrokeColorResource(R.color.black)
    }

    private fun setUpQuestionView(questions: ArrayList<Question>, questionId: Int) {
        var current_question: Question = this.questions.get(questionId-1)
        var current_question_title: String = current_question.question
        tv_question_title.text = current_question_title
    }

    private fun getQuestionData(): ArrayList<Question> {
        var questions:ArrayList<Question> = arrayListOf()
        firestore.collection("health_assessment").orderBy("id",Query.Direction.ASCENDING).get()
            .addOnSuccessListener { documents->
                for(document in documents){
                    var question: Question = document.toObject(Question::class.java)
                    questions.add(question)
                    Log.d("questions",questions.toString())
                }

                Log.d("questions",questions.toString())
            }.addOnFailureListener { e->
                Log.e("questions", "Failed on: "+e.message)
            }
        return questions

    }

    companion object {

        @JvmStatic
        fun newInstance() = HealthAssessmentFragment()
    }
}