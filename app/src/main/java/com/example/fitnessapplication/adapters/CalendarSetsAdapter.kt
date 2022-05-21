package com.example.fitnessapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetCalendarItemBinding
import com.example.fitnessapplication.databinding.SetChooseItemBinding
import db.CalendarSetDataEntity
import db.SetDataEntity

class CalendarSetsAdapter(private val dataList: List<CalendarSetDataEntity>) : RecyclerView.Adapter<CalendarSetsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = SetCalendarItemBinding.bind(view)

        fun bind(name: CalendarSetDataEntity) = with(binding){
            calendarNametv.text = name.name
            calendarTimetv.text = name.time
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarSetsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_calendar_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarSetsAdapter.ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}