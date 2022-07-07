package com.example.coviddefender.HomeFragmentSubpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.RecyclerViewAdapter.QuestionAdapter
import com.example.coviddefender.entity.AnswerSelected
import com.example.coviddefender.entity.Question
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HealthAssessmentFragment : Fragment() {
    lateinit var question_rv: RecyclerView

    lateinit var btn_back: ImageButton
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var btn_submit: MaterialButton

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String

    // Firestore
    private lateinit var firestore: FirebaseFirestore

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

        question_rv = view.findViewById(R.id.question_rv)
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
        btn_back?.setOnClickListener {
            findNavController().navigate(R.id.action_health_assessment_to_home)

        }

        setUpRecyclerView()

        btn_submit.setOnClickListener {
            val selectedList: ArrayList<AnswerSelected> = questionAdapter.selected
            var isCompleted: Boolean = true
            if (selectedList.size.equals(questionAdapter.itemCount)) {
                isCompleted = true
            } else {
                isCompleted = false
            }

            if (isCompleted) {
                var docRef: DocumentReference =
                    firestore.collection("health_assessment_records").document(userId)
                var time: Timestamp = Timestamp.now()
                var map = hashMapOf(
                    "submit_time" to time,
                    "answers" to selectedList
                )
                docRef.collection("records").add(map)
                    .addOnSuccessListener {
                        Log.d("HA", "Successfully saved answer")
                        findNavController().navigate(R.id.home)
                    }
                    .addOnFailureListener { e ->
                        Log.d("HA", e.message.toString())
                    }
            } else {
                Toast.makeText(context, "Please complete all the questions", Toast.LENGTH_SHORT)
                    .show()
            }

            // count number of
            var num_yes: Int = 0
            for (i in 0 until selectedList.size) {
                if (selectedList.get(i).answer.equals("yes")) {
                    num_yes += 1
                }
            }
            // if yes > no, High Risk
            if (num_yes > questionAdapter.itemCount - num_yes) {
                var docRef: DocumentReference =
                    firestore.collection("covid_status").document(userId)
                docRef.update("covid_status", "High Risk").addOnSuccessListener {
                    Toast.makeText(context, "Your covid status is updated", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("covidstatus", "High Risk")
                }
            }
            // if no >= yes, Low Risk
            else {
                var docRef: DocumentReference =
                    firestore.collection("covid_status").document(userId)
                docRef.update("covid_status", "Low Risk").addOnSuccessListener {
                    Toast.makeText(context, "Your covid status is updated", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("covidstatus", "Low Risk")
                }
            }

        }

        return view
    }

    private fun setUpRecyclerView() {
        var query: Query =
            firestore.collection("health_assessment").orderBy("id", Query.Direction.ASCENDING)

        var options: FirestoreRecyclerOptions<Question> =
            FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question::class.java)
                .build()

        questionAdapter = QuestionAdapter(options)

        // setting layout malinger to recycler view
        question_rv.layoutManager = LinearLayoutManager(
            view?.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        question_rv.adapter = questionAdapter


    }


    private fun getQuestionData(): ArrayList<Question> {
        var questions: ArrayList<Question> = arrayListOf()
        firestore.collection("health_assessment").orderBy("id", Query.Direction.ASCENDING).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var question: Question = document.toObject(Question::class.java)
                    questions.add(question)
                    Log.d("questions", questions.toString())
                }

                Log.d("questions", questions.toString())
            }.addOnFailureListener { e ->
                Log.e("questions", "Failed on: " + e.message)
            }
        return questions

    }

    override fun onStart() {
        super.onStart()
        questionAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        questionAdapter.stopListening()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HealthAssessmentFragment()
    }
}