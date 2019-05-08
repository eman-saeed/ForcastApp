package com.example.forcastapp.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.forcastapp.model.City;
import com.example.forcastapp.model.ForecastResponse;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.netwrok.model.ServiceProvider;
import com.example.forcastapp.netwrok.services.ForecastService;
import com.example.forcastapp.netwrok.services.WeatherService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForecastRepository {

    private static String appId = "b6907d289e10d714a6e88b30761fae22";
    private MutableLiveData<WeatherResponse> weatherLiveData;
    private MutableLiveData<ForecastResponse> forecastLiveData;

    public MutableLiveData<WeatherResponse> getWeather(String cityName) {
        weatherLiveData = new MutableLiveData<>();
        getWeatherFromServiceCAll(cityName);
        return weatherLiveData;
    }

    public MutableLiveData<ForecastResponse> getFiveDaysForecast(String cityName) {
        forecastLiveData = new MutableLiveData<>();
        getFiveDaysForecastFromService(cityName);
        return forecastLiveData;
    }

    private void getWeatherFromServiceCAll(String cityName) {
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
    }

    private void getFiveDaysForecastFromService(String cityName) {
        ForecastService forecastService = ServiceProvider.createRetrofitService(ForecastService.class);
        forecastService.getForecastByCity(cityName, appId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ForecastResponse forecastResponse) {
                        if (forecastResponse != null && forecastResponse.getCode() == 200) {
                            forecastLiveData.setValue(forecastResponse);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", "error");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public ArrayList<City> getAllCities(Context context) {

        String json = loadJSONFromAsset(context);

        Gson gson = new Gson();
        if (json != null) {
            Type listType = new TypeToken<ArrayList<City>>() {
            }.getType();
            return (ArrayList<City>) gson.fromJson(json, listType);
        }
        return null;
    }

    private InputStream getStreamOnFile(String fileName, Context context) {
        try {
            return context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = getStreamOnFile("city_list.json", context);
            if (is != null) {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //**************************************Singleton pattern************************************************
    private static ForecastRepository ourInstance;

    private ForecastRepository() {
    }

    public static ForecastRepository getInstance() {
        if (ourInstance == null) {
            ourInstance = new ForecastRepository();
        }
        return ourInstance;
    }
}
