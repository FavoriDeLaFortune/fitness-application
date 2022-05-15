package com.example.fitnessapplication.ui.sets

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
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
                    && time.editText?.text.toString() != "" && calories.editText?.text.toString() != ""
                    && isCorrectTime(time.editText?.text.toString()) && calories.editText?.length()!! >= 3) {
                    dataModel.apply {
                        nameData.value = name.editText?.text.toString()
                        descriptionData.value = description.editText?.text.toString()
                        timeData.value = time.editText?.text.toString()
                        caloriesData.value = calories.editText?.text.toString()
                        Toast.makeText(context, "Set successfully created!", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                } else {
                    if (name.editText?.text.toString() == "" || description.editText?.text.toString() == ""
                        || time.editText?.text.toString() == "" || calories.editText?.text.toString() == "") {
                        Toast.makeText(context, "Fill all fields!", Toast.LENGTH_LONG).show();
                    } else if (calories.editText?.length()!! < 3) {
                        calories.editText!!.setText("")
                        Toast.makeText(context, "Uncorrect calories range!", Toast.LENGTH_LONG).show();
                    } else {
                        time.editText?.setText("")
                        Toast.makeText(context, "Uncorrect time format!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            time.editText?.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                @SuppressLint("SetTextI18n")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (time.editText!!.text.toString().length == 2 && before == 0) {
                        time.editText!!.setText(time.editText!!.text.toString() + ":")
                        time.editText!!.setSelection(time.editText!!.length())
                    }
                    Log.d("before", before.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
        }
    }

    fun isCorrectTime(time: String) : Boolean {
        if (time.length < 5) {
            return false
        }
        if (time[0] >= '7' || time[3] >= '6' || time[0] == '6' && (time[1] > '0' || time[3] > '0' ||
                    time[4] > '0')) {
            return false
        }
        return true
    }
}