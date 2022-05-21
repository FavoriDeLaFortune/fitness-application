package com.example.fitnessapplication.ui.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                    @SuppressLint("CommitPrefEdits")
                    override fun onItemClick(position: Int) {
                        Log.d("item", "$position")
                        val clickedItem = list[position]
                        GlobalScope.launch {
                            calendarViewModel.insert(clickedItem.name, clickedItem.time)
                        }
                        Toast.makeText(context, "You chose ${clickedItem.name}", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                })
            }
        }

    }
}