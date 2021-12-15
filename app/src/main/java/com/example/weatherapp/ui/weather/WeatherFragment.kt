package com.example.weatherapp.ui.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.entities.relations.Weather
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.ui.search.LocationAdapter
import com.example.weatherapp.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel by viewModels<WeatherViewModel>()

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWeatherBinding.bind(view)

        val adapter = HourlyWeatherAdapter()

        binding.apply {
            recyclerView.adapter = adapter
            viewModel.weather.observe(viewLifecycleOwner){ result ->
                textViewLocation.text = result.data?.location?.name ?: ""
                textViewCurrentTemp.text = result.data?.currentWeather?.temp.toString()
                textViewWeather.text = result.data?.currentWeather?.firstWeather?.main ?: ""
                //textViewCurrentTime.text = result.data?.currentWeather?.dt?.let { Date(it * 1000).toString() }

                adapter.submitList(result.data?.hourlyWeather)
            }
        }
    }
}