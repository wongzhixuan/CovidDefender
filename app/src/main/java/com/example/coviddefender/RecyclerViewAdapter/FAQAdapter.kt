package com.example.coviddefender.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.entity.FAQ

class FAQAdapter(var faq: ArrayList<FAQ>) :
    RecyclerView.Adapter<FAQAdapter.FAQViewHolder>()  // interface
{
    // Second Called
    // Constructor for viewholder
    // provide reference to the type of views that you are using
    class FAQViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById<TextView>(R.id.faq_question)
        val answer: TextView = itemView.findViewById<TextView>(R.id.faq_answer)
        fun bind(faq: FAQ) {
            answer.text = faq.description
            question.text = faq.faqquestion
        }
    }

    // First function called
    // create new views (invoked by layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faq_item,parent,false)

        return FAQViewHolder(view)
    }

    // Third called
    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(faq[position])
    }

    override fun getItemCount(): Int = faq.size
}