package com.example.forcastapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eman
 */

public class Main {

    @SerializedName("temp")
    double temp;
    @SerializedName("pressure")
    double pressure;
    @SerializedName("humidity")
    double humidity;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
