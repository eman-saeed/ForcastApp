package com.example.forcastapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eman
 */

public class WeatherResponse extends BaseResponse{

    @SerializedName("weather")
    ArrayList<Weather> weatherArrayList;
    @SerializedName("main")
    Main main;
    @SerializedName("wind")
    Wind wind;
    @SerializedName("clouds")
    Clouds clouds;

    public ArrayList<Weather> getWeatherArrayList() {
        return weatherArrayList;
    }

    public void setWeatherArrayList(ArrayList<Weather> weatherArrayList) {
        this.weatherArrayList = weatherArrayList;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
}
