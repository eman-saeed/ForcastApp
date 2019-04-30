package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eman
 */

public class ForecastList {
    @SerializedName("dt")
    double dt;
    @SerializedName("main")
    Main main;
    @SerializedName("weather")
    Weather[] weather;
    @SerializedName("clouds")
    Clouds clouds;
    @SerializedName("wind")
    Wind wind;
    @SerializedName("dt_txt")
    String dateText;

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}
