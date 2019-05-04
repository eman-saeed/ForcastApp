package com.example.forcastapp.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.forcastapp.model.City;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.netwrok.model.ServiceProvider;
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

    private static String appId = "b5b19a4019f771d7da6ccddc1ce9f213";
    private static ForecastRepository ourInstance;
    private MutableLiveData<WeatherResponse> weatherLiveData;

    public MutableLiveData<WeatherResponse> getWeather(String cityName) {
        weatherLiveData = new MutableLiveData<>();
        getWeatherFromServiceCAll(cityName);
        return weatherLiveData;
    }

    private WeatherResponse getStaticWeather() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCityName("Benha");
        return weatherResponse;
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
