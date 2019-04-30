package com.example.forcastapp.netwrok.services;

import com.example.forcastapp.model.ForecastResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eman
 */

public interface ForecastService {
    @GET("forecast")
    Observable<ForecastResponse> getForecastByCity(@Query("q") String q, @Query("appid") String appId);
}
