package com.example.weatherapp.ui.weather

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

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
            viewModel.weather.observe(viewLifecycleOwner) { result ->

                if(!result.data?.hourlyWeather.isNullOrEmpty()){
                    imageView.isVisible = true
                    textViewCurrentTemp.isVisible = true
                    textViewWeather.isVisible = true
                    recyclerView.isVisible = true
                    LinearLayout.isVisible = true
                    textViewOc.isVisible = true
                }

                progressBar.isVisible = result is Resource.Loading && (result.data?.hourlyWeather.isNullOrEmpty())
                textViewError.isVisible = result is Resource.Error && (result.data?.hourlyWeather.isNullOrEmpty())
                buttonRetry.isVisible = result is Resource.Error && (result.data?.hourlyWeather.isNullOrEmpty())
                textViewEmpty.isVisible = result is Resource.Success && (result.data?.hourlyWeather.isNullOrEmpty())
                textViewError.text = result.error?.localizedMessage

                Glide.with(imageView)
                    .load(result.data?.currentWeather?.firstWeather?.url)
                    .into(imageView)
                textViewCurrentTemp.text = result.data?.currentWeather?.readableTemp
                textViewWeather.text = result.data?.currentWeather?.firstWeather?.main ?: ""

                dailyWeatherToday.apply {
                    with(result.data?.dailyWeather?.getOrNull(0)){
                        Glide.with(imageViewHourly)
                            .load(this?.firstWeather?.url)
                            .into(imageViewHourly)
                        textViewWeather.text = "Today · " + this?.firstWeather?.main
                        textViewTemp.text = this?.temp?.readableDayTemp + " / " + this?.temp?.readableNightTemp
                    }
                }
                dailyWeatherTomorrow.apply {
                    with(result.data?.dailyWeather?.getOrNull(1)){
                        Glide.with(imageViewHourly)
                            .load(this?.firstWeather?.url)
                            .into(imageViewHourly)
                        textViewWeather.text = "Tomorrow · " + this?.firstWeather?.main
                        textViewTemp.text = this?.temp?.readableDayTemp + " / " + this?.temp?.readableNightTemp
                    }
                }
                dailyWeatherDayAfterTomorrow.apply {
                    with(result.data?.dailyWeather?.getOrNull(2)){
                        Glide.with(imageViewHourly)
                            .load(this?.firstWeather?.url)
                            .into(imageViewHourly)
                        textViewWeather.text = viewModel.getDayOfWeek(this?.dt) + " · " + this?.firstWeather?.main
                        textViewTemp.text = this?.temp?.readableDayTemp + " / " + this?.temp?.readableNightTemp
                    }
                }

                adapter.submitList(result.data?.hourlyWeather)
            }
        }
    }
}