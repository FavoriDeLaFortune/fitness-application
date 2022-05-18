package com.example.fitnessapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetItemBinding
import db.Set
import java.util.*
import kotlin.collections.ArrayList

class SetsAdapter(private val dataList: ArrayList<Set>) : RecyclerView.Adapter<SetsAdapter.ViewHolder>() {


    fun insertItem(newItem: Set) {
        dataList.add(0, newItem)
        notifyItemInserted(0)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = SetItemBinding.bind(view)

        fun bind(name: Set) = with(binding){
            nametv.text = name.name
            timetv.text = "Time: " + name.time
            caltv.text = "Cal: " + name.calories
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.set_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}