package com.example.fitnessapplication.ui.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.format.Time
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.adapters.ChooseSetsAdapter
import com.example.fitnessapplication.databinding.FragmentChooseSetBinding
import com.example.fitnessapplication.ui.sets.SetsViewModel
import db.SetDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime

class SetChooseFragment : Fragment() {

    private val calendarViewModel: CalendarViewModel by activityViewModels()
    private val setsViewModel: SetsViewModel by viewModels()
    private lateinit var binding: FragmentChooseSetBinding


    override  fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChooseSetBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            val list: List<SetDataEntity> = setsViewModel.databaseDao.getAll()
            val adapter = ChooseSetsAdapter(list)
            withContext(Dispatchers.Main) {
                val recyclerView: RecyclerView = view.findViewById(R.id.choose_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter
                adapter.setOnItemListener(object : ChooseSetsAdapter.Listener{
                    @RequiresApi(Build.VERSION_CODES.M)
                    @SuppressLint("CommitPrefEdits")
                    override fun onItemClick(position: Int) {
                        val clickedItem = list[position]
                        val timePicker: TimePicker = view.findViewById(R.id.time_picker)
                        val btn: Button = view.findViewById(R.id.time_picker_btn)
                        timePicker.alpha = 0f
                        timePicker.visibility = View.VISIBLE
                        timePicker.animate().alpha(1.0f)
                        timePicker.setIs24HourView(true)
                        btn.alpha = 0f
                        btn.visibility = View.VISIBLE
                        btn.animate().alpha(1.0f)
                        timePicker.setOnTimeChangedListener { _, hour, minute ->
                            btn.setOnClickListener {
                                val pref: SharedPreferences? =
                                    context?.getSharedPreferences("KEY_VALUE", Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor? = pref?.edit()
                                val date: String? = pref?.getString("DATE_KEY", "")
                                val strMinute: String = if (minute < 10) {
                                    "0$minute"
                                } else {
                                    minute.toString()
                                }
                                val strHour: String = if (hour < 10) {
                                    "0$hour"
                                } else {
                                    hour.toString()
                                }
                                editor?.clear()
                                editor?.apply()
                                GlobalScope.launch {
                                    if (date != null) {
                                        var endTime = (LocalTime.parse("$strHour:$strMinute")
                                            .plusMinutes((clickedItem.time[0].toString() +
                                                    clickedItem.time[1].toString()).toLong())
                                            .plusSeconds((clickedItem.time[3].toString() +
                                                    clickedItem.time[4].toString()).toLong()).toString())
                                        if (endTime.length < 7) {
                                            endTime += ":00"
                                        }
                                        calendarViewModel.insert(date, clickedItem.name, (LocalTime
                                            .parse("$strHour:$strMinute").toString()
                                                + ":00 - " + endTime))
                                    }
                                }
                                Toast.makeText(context, "You chose ${clickedItem.name}", Toast.LENGTH_LONG).show()
                                findNavController().popBackStack()
                            }
                        }
                    }
                })
            }
        }

    }
}