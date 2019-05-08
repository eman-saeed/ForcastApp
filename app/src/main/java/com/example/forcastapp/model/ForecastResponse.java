package com.example.forcastapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eman
 */

public class ForecastResponse extends BaseResponse{

    @SerializedName("list")
    ArrayList<Forecast> forecasts;

    public ArrayList<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(ArrayList<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
