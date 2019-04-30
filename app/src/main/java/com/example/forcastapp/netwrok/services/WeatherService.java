package com.example.forcastapp.netwrok.services;

import com.example.forcastapp.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by EMAN
 */
public interface WeatherService {

    @GET("weather")
    Observable<WeatherResponse> getWeatherByCityAndCountry(@Query("q") String q, @Query("appid") String appId);

}
