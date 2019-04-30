package com.example.forcastapp.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.forcastapp.model.Weather;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.netwrok.model.ServiceProvider;
import com.example.forcastapp.netwrok.services.WeatherService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForcastRepository {

    private static ForcastRepository ourInstance;


    public MutableLiveData<WeatherResponse> getWeather(String cityName, String appId) {
        MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();
        weatherLiveData.setValue(getWeatherFromServiceCAll(cityName, appId));
        return weatherLiveData;
    }


    private WeatherResponse getWeatherFromServiceCAll(String cityName, String appId) {
        final WeatherResponse[] weather = {null};
        WeatherService weatherService = ServiceProvider.createRetrofitService(WeatherService.class);
        weatherService.getWeatherByCityAndCountry(cityName, appId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WeatherResponse weatherResponse) {
                        if (weatherResponse != null && weatherResponse.getCode() == 200) {
                        } else {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return weather[0];

    }

    //**************************************instance************************************************
    private ForcastRepository() {
    }

    public static ForcastRepository getInstance() {
        if (ourInstance == null) {
            ourInstance = new ForcastRepository();
        }
        return ourInstance;
    }
}
