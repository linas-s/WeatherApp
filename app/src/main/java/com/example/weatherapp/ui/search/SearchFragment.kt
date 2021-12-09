package com.example.weatherapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)

        val adapter = LocationWeatherAdapter()

        binding.apply {
            recyclerView.adapter = adapter
        }

        viewModel.weatherLocations.observe(viewLifecycleOwner){ locations ->
            adapter.submitList(locations)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}