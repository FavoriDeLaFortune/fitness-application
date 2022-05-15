package com.example.fitnessapplication.ui.sets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetAddBinding
import com.example.fitnessapplication.ui.calendar.setTextColorRes

class SetAddFragment : Fragment(R.layout.set_add) {
    private lateinit var binding: SetAddBinding
    private val dataModel: SetsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SetAddBinding.bind(view)

        binding.apply {
            btn.setOnClickListener() {
                if (name.editText?.text.toString() != "" && description.editText?.text.toString() != ""
                    && time.editText?.text.toString() != "" && calories.editText?.text.toString() != "") {
                    dataModel.apply {
                        nameData.value = name.editText?.text.toString()
                        descriptionData.value = description.editText?.text.toString()
                        timeData.value = time.editText?.text.toString()
                        caloriesData.value = calories.editText?.text.toString()
                        Toast.makeText(context, "Set successfully created!", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                } else {
                    Toast.makeText(context, "Fill all fields!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}