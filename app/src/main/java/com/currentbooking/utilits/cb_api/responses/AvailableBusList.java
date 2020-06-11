package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AvailableBusList extends BaseResponse {
    @SerializedName("data")
    @Expose
    BusListObj busListObj;

    public BusListObj getBusListObj() {
        return busListObj;
    }

    public void setBusListObj(BusListObj busListObj) {
        this.busListObj = busListObj;
    }

    public class BusListObj {
        @SerializedName("result")
        @Expose
        ArrayList<BusObject> busList;

        public ArrayList<BusObject> getBusList() {
            return busList;
        }

        public void setBusList(ArrayList<BusObject> busList) {
            this.busList = busList;
        }
    }
}
