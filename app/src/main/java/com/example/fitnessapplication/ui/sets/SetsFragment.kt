package com.example.fitnessapplication.ui.sets

import android.os.Bundle
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
import com.example.fitnessapplication.sets.SetsAdapter
import java.util.ArrayList

class SetsFragment : Fragment() {

    private lateinit var setsViewModel: SetsViewModel
    private var _binding: FragmentSetsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setsViewModel =
            ViewModelProvider(this).get(SetsViewModel::class.java)

        _binding = FragmentSetsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = ArrayList<String>()
        dataList.add("Russkiy")
        dataList.add("rap")
        dataList.add("kruto")
        dataList.add("tem bolee")
        dataList.add("XAN ZAMAY")
        val adapter = SetsAdapter(dataList)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val fabSet: View = view.findViewById(R.id.fab)
        fabSet.setOnClickListener() {
            findNavController().navigate(R.id.action_navigation_sets_to_navigation_set_add)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}