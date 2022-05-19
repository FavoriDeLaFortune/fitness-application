package com.example.fitnessapplication.adapters

import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetChooseItemBinding
import com.example.fitnessapplication.ui.calendar.SetChooseFragment
import db.SetDataEntity

class ChooseSetsAdapter(private val dataList: List<SetDataEntity>, val listener: SetChooseFragment)
    : RecyclerView.Adapter<ChooseSetsAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = SetChooseItemBinding.bind(view)

        fun bind(name: SetDataEntity) = with(binding){
            chooseNametv.text = name.name
            chooseTimetv.text = "Time: " + name.time
            chooseCaltv.text = "Cal: " + name.calories
            if (checkbox.isChecked) {

            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.set_choose_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface Listener {
        fun Checked(set: SetDataEntity)
    }

}