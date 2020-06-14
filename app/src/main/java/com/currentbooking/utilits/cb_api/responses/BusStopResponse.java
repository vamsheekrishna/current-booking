package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusStopResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    BusStopList busStopList;

    public BusStopList getBusStopList() {
        return busStopList;
    }

    public void setBusStopList(BusStopList busStopList) {
        this.busStopList = busStopList;
    }

    public class BusStopList {
        @SerializedName("result")
        @Expose
        ArrayList<BusStopObject> busStopList;

        public ArrayList<BusStopObject> getBusStopList() {
            return busStopList;
        }

        public void setBusStopList(ArrayList<BusStopObject> busStopList) {
            this.busStopList = busStopList;
        }
    }
}
