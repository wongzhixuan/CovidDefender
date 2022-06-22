package com.example.coviddefender.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.db.toknow.ToKnow

class ToKnowAdapter(var toknow: ArrayList<ToKnow>) :
    RecyclerView.Adapter<ToKnowAdapter.ToKnowViewHolder>()  // interface
{
    // Second Called
    // Constructor for viewholder
    // provide reference to the type of views that you are using
    class ToKnowViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById<ImageView>(R.id.card_things_to_know_1)
        val tv_description: TextView = itemView.findViewById<TextView>(R.id.tv_things_to_know_1)
        fun bind(toknow: ToKnow) {
            tv_description.text = toknow.description
            thumbnail.setImageResource(toknow.thumbnail)
        }
    }

    // First function called
    // create new views (invoked by layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToKnowViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.things_to_know_item,parent,false)

        return ToKnowViewHolder(view)
    }

    // Third called
    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ToKnowViewHolder, position: Int) {
        holder.bind(toknow[position])
    }

    override fun getItemCount(): Int = toknow.size
}