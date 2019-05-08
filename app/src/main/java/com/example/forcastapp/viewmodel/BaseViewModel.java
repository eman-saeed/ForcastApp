package com.example.forcastapp.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.forcastapp.repositories.ForecastRepository;

public class BaseViewModel extends ViewModel {

    protected ForecastRepository forecastRepository;

    {
        forecastRepository = ForecastRepository.getInstance();
    }
}
