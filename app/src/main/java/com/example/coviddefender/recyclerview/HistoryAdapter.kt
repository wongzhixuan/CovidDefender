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
import java.security.AccessController.getContext

class HistoryAdapter(var historys: ArrayList<History>):
RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>()
{
    // viewholder constructor
    class HistoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val card_history_icon: ImageView = itemView.findViewById<ImageView>(R.id.card_history_icon)
        val tv_location_name: TextView = itemView.findViewById<TextView>(R.id.tv_location_name)
        val tv_checkin_time: TextView = itemView.findViewById<TextView>(R.id.tv_checkin_time)
        val btn_check_out: MaterialButton =
            itemView.findViewById<MaterialButton>(R.id.btn_check_out)

        fun bind(history: History) {
            card_history_icon.setImageResource(history.thumbnail)
            tv_location_name.text = history.location_name
            tv_checkin_time.text = history.checkin_time
            if(history.check_out_state ){
                btn_check_out.setStrokeColorResource(R.color.dim_grey_light)
                btn_check_out.setTextColor(Color.GRAY)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)

        return HistoryAdapter.HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historys[position])
    }

    override fun getItemCount(): Int {
        return historys.size
    }
}