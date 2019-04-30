package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eman
 */

public class Clouds {

    @SerializedName("all")
    double all;

    public double getAll() {
        return all;
    }

    public void setAll(double all) {
        this.all = all;
    }
}
