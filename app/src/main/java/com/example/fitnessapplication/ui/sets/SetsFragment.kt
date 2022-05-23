package com.example.fitnessapplication.ui.sets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.FragmentSetsBinding
import com.example.fitnessapplication.adapters.SetsAdapter
import db.SetDao
import db.SetDataEntity
import db.SetDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetsFragment : Fragment() {

    private val setsViewModel: SetsViewModel by viewModels()
    private var _binding: FragmentSetsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSetsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            val list: List<SetDataEntity> = setsViewModel.databaseDao.getAll()
            val adapter = SetsAdapter(list)
            withContext(Dispatchers.Main) {
                if (list.isEmpty()) {
                    binding.nosetsTv.visibility = View.VISIBLE
                } else {
                    binding.nosetsTv.visibility = View.GONE
                }
                val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter
            }
        }

        val fabSet: View = view.findViewById(R.id.fab)
        fabSet.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sets_to_navigation_set_add)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}