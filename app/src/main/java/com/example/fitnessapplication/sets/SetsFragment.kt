package com.example.fitnessapplication.sets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import java.util.ArrayList


class SetsFragment : Fragment(R.layout.fragment_sets) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = ArrayList<String>()
        dataList.add("Russkiy")
        dataList.add("rap")
        dataList.add("kruto")
        dataList.add("tem bolee")
        dataList.add("XAN ZAMAY")
        val adapter = SetsAdapter(dataList)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}