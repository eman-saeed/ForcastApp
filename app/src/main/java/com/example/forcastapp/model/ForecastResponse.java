package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eman
 */

public class ForecastResponse extends BaseResponse{

    @SerializedName("list")
    ArrayList<ForecastList> forecastLists;

    public ArrayList<ForecastList> getForecastLists() {
        return forecastLists;
    }

    public void setForecastLists(ArrayList<ForecastList> forecastLists) {
        this.forecastLists = forecastLists;
    }
}
