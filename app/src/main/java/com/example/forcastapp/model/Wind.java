package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eman
 */

public class Wind {

    @SerializedName("speed")
    double windSpeed;
    @SerializedName("deg")
    double windDegree;

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }
}
