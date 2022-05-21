package com.example.coviddefender.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.google.android.material.button.MaterialButton

class VaccineInfoAdapter(var infos:ArrayList<Vaccine_Info>) :
RecyclerView.Adapter<VaccineInfoAdapter.VaccineInfoViewHolder>()
{
    // viewholder constructor
    class VaccineInfoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById<ImageView>(R.id.image_vaccine_info)
        val tv_description: TextView = itemView.findViewById<TextView>(R.id.tv_vaccine_info_description)

        fun bind(vaccineInfo: Vaccine_Info) {
            thumbnail.setImageResource(vaccineInfo.thumbnail)
            tv_description.text = vaccineInfo.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineInfoViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vaccine_info_items, parent, false)

        return VaccineInfoAdapter.VaccineInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaccineInfoViewHolder, position: Int) {
        holder.bind(infos[position])
    }

    override fun getItemCount(): Int {
        return infos.size
    }
}