package com.example.fitnessapplication.ui.sets

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.SetAddBinding

class SetAddFragment : Fragment(R.layout.set_add) {
    private lateinit var binding: SetAddBinding
    private val setsViewModel: SetsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SetAddBinding.bind(view)

        binding.apply {
            btn.setOnClickListener {
                if (name.editText?.text.toString() != "" && description.editText?.text.toString() != ""
                    && time.editText?.text.toString() != "" && calories.editText?.text.toString() != ""
                    && isCorrectTime(time.editText?.text.toString()) && isCorrectCalories(calories.editText?.text.toString())) {

                    setsViewModel.update(name.editText?.text.toString(), description.editText?.text.toString(),
                        time.editText?.text.toString(), calories.editText?.text.toString())
                    Toast.makeText(context, "Set successfully created!", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    if (name.editText?.text.toString() == "" || description.editText?.text.toString() == ""
                        || time.editText?.text.toString() == "" || calories.editText?.text.toString() == "") {
                        Toast.makeText(context, "Fill all fields!", Toast.LENGTH_LONG).show()
                    } else if (!isCorrectCalories(calories.editText?.text.toString())) {
                        calories.editText!!.setText("")
                        Toast.makeText(context, "Uncorrect calories range!", Toast.LENGTH_LONG).show()
                    } else {
                        time.editText?.setText("")
                        Toast.makeText(context, "Uncorrect time format!", Toast.LENGTH_LONG).show()
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

    private fun isCorrectTime(time: String) : Boolean {
        if (time.length < 5) {
            return false
        }
        if (time[0] >= '7' || time[3] >= '6' || time[0] == '6' && (time[1] > '0' || time[3] > '0' ||
                    time[4] > '0')) {
            return false
        }
        return true
    }

    private fun isCorrectCalories(calories: String) : Boolean {
        if (calories.length < 3) {
            return false
        }
        if (calories[0] == '0') {
            return false
        }
        return true
    }
}