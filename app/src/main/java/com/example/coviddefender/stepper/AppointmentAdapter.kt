package com.example.coviddefender.stepper

import com.transferwise.sequencelayout.SequenceAdapter
import com.transferwise.sequencelayout.SequenceStep


class AppointmentAdapter(var items: ArrayList<MyItem>):
SequenceAdapter<AppointmentAdapter.MyItem>()
{
    data class MyItem(
        var isActive: Boolean,
        var formattedDate: String,
        var title: String,
        var subtitle: String
    )

    override fun bindView(sequenceStep: SequenceStep, item: MyItem) {
        with(sequenceStep){
            setActive(item.isActive)
            setAnchor(item.formattedDate)
            setTitle(item.title)
            setSubtitle(item.subtitle)
        }
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): MyItem {
        return items[position]
    }
}