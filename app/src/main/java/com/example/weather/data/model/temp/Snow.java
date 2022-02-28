package com.example.weather.data.model.temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {
    @SerializedName("3h")
    @Expose
    private Double H_3;

    public Double getH_3() {
        return H_3;
    }

    public void setH_3(Double h_3) {
        H_3 = h_3;
    }
}
