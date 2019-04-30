package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eman
 */

public class Weather {

    @SerializedName("id")
    int id;
    @SerializedName("main")
    String main;
    @SerializedName("description")
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
