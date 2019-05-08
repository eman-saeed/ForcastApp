package com.example.forcastapp.utils;

public class StringsUtil {

    public static String getWindString(String wind) {
        return wind + " mph";
    }

    public static String getPressure(String pressure) {
        return pressure + " mb";
    }

    public static String getHumidity(String humidity) {
        return humidity + " %";
    }

    public static String getTemp(double temp) {
        return temp + " °C ";
    }
}
