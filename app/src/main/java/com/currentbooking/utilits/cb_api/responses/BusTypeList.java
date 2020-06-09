package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusTypeList extends BaseResponse {
    @SerializedName("data")
    @Expose
    BusTypes busTypes;

    public BusTypes getBusTypes() {
        return busTypes;
    }

    public void setBusTypes(BusTypes busTypes) {
        this.busTypes = busTypes;
    }

    public class BusTypes {
        @SerializedName("result")
        @Expose
        ArrayList<BusType> busTypes;

        public ArrayList<BusType> getBusTypes() {
            return busTypes;
        }

        public void setBusTypes(ArrayList<BusType> busTypes) {
            this.busTypes = busTypes;
        }
    }
}
