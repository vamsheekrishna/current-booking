package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusOperatorList extends BaseResponse {

    @SerializedName("data")
    @Expose
    BusOperators busOperatorList;

    public BusOperators getBusOperatorList() {
        return busOperatorList;
    }

    public void setBusOperatorList(BusOperators busOperatorList) {
        this.busOperatorList = busOperatorList;
    }

    public class BusOperators {
        @SerializedName("result")@Expose
        ArrayList<BusOperator> busOperators;
        public ArrayList<BusOperator> getBusOperators() {
            return busOperators;
        }
        public void setBusOperators(ArrayList<BusOperator> busOperators) {
            this.busOperators = busOperators;
        }


    }
}
