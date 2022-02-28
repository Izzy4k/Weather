package com.example.weather.data.model.temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SysTemp {
    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}
