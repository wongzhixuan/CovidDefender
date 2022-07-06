package com.example.coviddefender.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.example.coviddefender.entity.Announcement

class AnnouncementAdapter(var announcements: ArrayList<Announcement>) :
    RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>()  // interface
{
    // Second Called
    // Constructor for viewholder
    // provide reference to the type of views that you are using
    class AnnouncementViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById<ImageView>(R.id.announcement_image)
        val tv_description: TextView = itemView.findViewById<TextView>(R.id.announcement_text)
        fun bind(announcement: Announcement) {
            tv_description.text = announcement.description
            thumbnail.setImageResource(announcement.thumbnail)
        }
    }

    // First function called
    // create new views (invoked by layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.latest_announcement_item,parent,false)

        return AnnouncementViewHolder(view)
    }

    // Third called
    // replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(announcements[position])
    }

    override fun getItemCount(): Int = announcements.size
}