package com.example.fitnessapplication.ui.statistic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.FragmentStatisticBinding
import com.example.fitnessapplication.ui.calendar.CalendarViewModel
import com.example.fitnessapplication.ui.sets.SetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import kotlin.random.Random

class StatisticFragment : Fragment() {

    private val calendarViewModel: CalendarViewModel by viewModels()
    private val setsViewModel: SetsViewModel by viewModels()
    private var _binding: FragmentStatisticBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO){
            val list = calendarViewModel.get()
            val quotesList = resources.getStringArray(R.array.quotes)
            val authorsList = resources.getStringArray(R.array.authors)
            val rand = (0..19).random()
            withContext(Dispatchers.Default) {
                var avgTime: LocalTime = LocalTime.parse("00:00")
                var avgCal = 0
                var str = ""
                var sec: Long = 0
                if (list.isNotEmpty()) {
                    for (i in list) {
                        avgCal += (setsViewModel.databaseDao.getByName(i.name))[0].calories.toInt()
                        sec += setsViewModel.databaseDao.getByName(i.name)[0].time
                            .subSequence(0, 2).toString().toLong() * 60 + setsViewModel.databaseDao
                            .getByName(i.name)[0].time.subSequence(3, 5).toString().toLong()
                        Log.d("statsec", "$sec")
                    }
                    avgCal /= list.size
                    val minutes = ((sec / 60) / list.size).toInt()
                    val seconds = ((sec - minutes * 60 * list.size) / list.size).toInt()
                    Log.d("minutes & sec", "$minutes + $seconds")
                    avgTime = LocalTime.of(0, minutes, seconds)
                    if (((sec / 60) / list.size).toInt() == 0) {
                        str = ":00"
                    } else {
                        str = ""
                    }
                }
                withContext(Dispatchers.Main) {
                    binding.avgCalTv.text = avgCal.toString()
                    binding.avgTimeTv.text = avgTime.toString() + "${str}"
                    binding.quoteTv.text = quotesList[rand]
                    binding.authorTv.text = authorsList[rand]
                }
            }
            withContext(Dispatchers.Main) {
                binding.cntSetsTv.text = "Count of sets: " + list.size.toString()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}