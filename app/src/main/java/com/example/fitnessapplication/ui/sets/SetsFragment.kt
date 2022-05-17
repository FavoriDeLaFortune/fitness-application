package com.example.fitnessapplication.ui.sets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapplication.R
import com.example.fitnessapplication.databinding.FragmentSetsBinding
import com.example.fitnessapplication.adapters.SetsAdapter

class SetsFragment : Fragment() {

    private lateinit var setsViewModel: SetsViewModel
    private var _binding: FragmentSetsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setsViewModel =
            ViewModelProvider(this)[SetsViewModel::class.java]

        _binding = FragmentSetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SetsAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        setsViewModel.nameData.observe(viewLifecycleOwner) {
            Log.d("added", "yes")
            adapter.insertItem(it)
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