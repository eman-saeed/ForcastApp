package com.example.forcastapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.forcastapp.model.City;
import com.example.forcastapp.model.WeatherResponse;

import java.util.ArrayList;

public class MainActivityViewModel extends BaseViewModel {

    public LiveData<WeatherResponse> getWeather(String city) {
        return forecastRepository.getWeather(city);
    }

    public ArrayList<City> getCities(Context context) {
        return forecastRepository.getAllCities(context);
    }
}
