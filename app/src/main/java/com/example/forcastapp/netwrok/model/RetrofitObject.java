package com.example.forcastapp.netwrok.model;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by EMAN
 */

public class RetrofitObject {

    private static final String BASE_URL = "https://openweathermap.org/data/2.5/";

    private static Retrofit ourInstance = null;

    public static synchronized Retrofit getInstance() {

        if (ourInstance == null) {
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return ourInstance;
    }
}