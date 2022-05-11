package com.example.fitnessapplication.ui.sets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetAddBinding

class SetAddFragment : Fragment(R.layout.set_add) {
    private lateinit var binding: SetAddBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SetAddBinding.bind(view)
    }
}