package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusStopObject {
    int id =-1;
    @SerializedName("stop_code")
    @Expose
    String stopCode = "";

    @SerializedName("stop_name")
    @Expose
    String stopName = "";

    public String getStopCode() {
        return stopCode;
    }

    public void setStopCode(String stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }
}
