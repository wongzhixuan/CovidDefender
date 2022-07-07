package com.example.coviddefender.futureimprovement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coviddefender.R
import com.google.android.material.checkbox.MaterialCheckBox

class DependentAdapter(var dependents: ArrayList<Dependent>):RecyclerView.Adapter<DependentAdapter.DependentViewHolder>() {
    class DependentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dependent_check_box: MaterialCheckBox = itemView.findViewById<MaterialCheckBox>(R.id.dependent_check_box)
        val tv_dependent_name: TextView = itemView.findViewById<TextView>(R.id.tv_dependent_name)
        val tv_dependent_relation: TextView = itemView.findViewById<TextView>(R.id.tv_dependent_relation)

        fun bind(dependent: Dependent) {
            tv_dependent_name.text = dependent.dependent_name
            tv_dependent_relation.text = dependent.dependent_relation
            if (dependent.checked) {
                dependent_check_box.isChecked = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DependentViewHolder {
        // create new view, defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dependent_item, parent, false)

        return DependentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DependentViewHolder, position: Int) {
        holder.bind(dependents[position])
    }

    override fun getItemCount(): Int {
        return dependents.size
    }
}