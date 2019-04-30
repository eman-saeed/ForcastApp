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

public class ForecastRepository {

    private static ForecastRepository ourInstance;
    private MutableLiveData<WeatherResponse> weatherLiveData;

    public MutableLiveData<WeatherResponse> getWeather(String cityName, String appId) {
        weatherLiveData = new MutableLiveData<>();
        weatherLiveData.setValue(getWeatherFromServiceCAll(cityName, appId));
        return weatherLiveData;
    }

    private WeatherResponse getStaticWeather() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCityName("Benha");
        return weatherResponse;
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
                            weatherLiveData.setValue(weatherResponse);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("from error", "error");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return weather[0];

    }

    //**************************************instance************************************************
    private ForecastRepository() {
    }

    public static ForecastRepository getInstance() {
        if (ourInstance == null) {
            ourInstance = new ForecastRepository();
        }
        return ourInstance;
    }
}
