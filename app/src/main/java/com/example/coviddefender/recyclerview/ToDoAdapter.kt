package com.example.coviddefender.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R

abstract class ToDoAdapter(var todo: ArrayList<ToDo>) :
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>()  // interface
{
    // Second Called
    // Constructor for viewholder
    // provide reference to the type of views that you are using
    class ToDoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        /*val thumbnail: ImageView = itemView.findViewById<ImageView>(R.id.todo_image)
        val tv_description: TextView = itemView.findViewById<TextView>(R.id.todo_text)
        fun bind(todo: ToDo) {
            tv_description.text = todo.description
            thumbnail.setImageResource(todo.thumbnail)*/
        }
    /*}

    // First function called
    // create new views (invoked by layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.latest_announcement_item,parent,false)

        return ToDoViewHolder(view)
    }

    // Third called
    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(todo[position])
    }

    override fun getItemCount(): Int = todo.size*/
}