package com.example.weatherapp.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.entities.WeatherLocation
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.util.Resource
import com.example.weatherapp.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), LocationAdapter.OnItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchQuery: String? = "London"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        val adapter = LocationAdapter(this)

        binding.apply {
            recyclerView.adapter = adapter
            viewModel.locations.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result.data)
                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                buttonRetry.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewEmpty.isVisible = result is Resource.Success && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage

                buttonRetry.setOnClickListener {
                    searchQuery?.let { it -> viewModel.searchLocation(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchEvent.collect { event ->
                when (event) {
                    is SearchViewModel.SearchEvent.NavigateToWeatherFragment -> {
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToWeatherFragment(
                                event.weatherLocation,
                                event.weatherLocation.name
                            )
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }



        setHasOptionsMenu(true)
    }

    override fun onItemClick(weatherLocation: WeatherLocation) {
        viewModel.onLocationSelected(weatherLocation)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    searchQuery = query
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchLocation(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}