package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eman
 */

public class BaseResponse {

    @SerializedName("id")
    double id;
    @SerializedName("name")
    String cityName;
    @SerializedName("cod")
    int code;
    @SerializedName("message")
    String message;
    @SerializedName("cnt")
    int cnt;

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }


}
