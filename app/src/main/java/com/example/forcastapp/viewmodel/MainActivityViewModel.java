package com.example.forcastapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.forcastapp.model.Weather;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.repositories.ForecastRepository;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<WeatherResponse> weatherResponseMutableLiveData;
    private ForecastRepository forecastRepository;

    {
        forecastRepository = ForecastRepository.getInstance();
    }

    public LiveData<WeatherResponse> getWeather(String city, String appId) {
        return forecastRepository.getWeather(city, appId);
    }
}
