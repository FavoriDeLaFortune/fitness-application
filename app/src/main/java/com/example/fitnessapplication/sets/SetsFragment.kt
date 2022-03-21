package com.example.fitnessapplication.sets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fitnessapplication.R


class SetsFragment : Fragment(R.layout.fragment_sets) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = context.resources.getStringArray(R.array.sets_array)
        val adapter = SetsAdapter(data)
    }
}