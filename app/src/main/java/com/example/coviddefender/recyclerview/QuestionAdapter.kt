package com.example.coviddefender.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton

class QuestionAdapter ( var questions: ArrayList<Question>) :
RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>()  // interface
{
    // Second Called
    // Constructor for viewholder
    // provide reference to the type of views that you are using
    class QuestionViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tv_question_title: TextView = itemView.findViewById<TextView>(R.id.tv_question_title)
        val btn_answer_no: MaterialButton = itemView.findViewById(R.id.btn_answer_no)
        val btn_answer_yes: MaterialButton = itemView.findViewById(R.id.btn_answer_yes)
        fun bind(question:Question) {
            tv_question_title.text = question.question_title

        }
    }

    // First function called
    // create new views (invoked by layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.questions_item, parent, false)

        return QuestionViewHolder(view)
    }

    // Third called
    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size
}