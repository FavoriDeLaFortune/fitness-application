package com.example.fitnessapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetItemBinding

class SetsAdapter() : RecyclerView.Adapter<SetsAdapter.ViewHolder>() {
    val dataList = ArrayList<String>()

    fun setData(newDataList: ArrayList<String>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }

    fun insertItem(newItem: String) {
        dataList.add(0, newItem)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = SetItemBinding.bind(view)

        fun bind(name: String) = with(binding){
            nametv.text = name
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