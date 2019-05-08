package com.example.forcastapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.example.forcastapp.model.ForecastResponse;

public class ForecastActivityViewModel extends BaseViewModel {

    public MutableLiveData<ForecastResponse> getFiveDaysForecast(String cityName) {
        return forecastRepository.getFiveDaysForecast(cityName);
    }
}
