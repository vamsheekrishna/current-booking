package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetFareResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    FareResultDetails fareResultDetails;

    public FareResultDetails getFareDetails() {
        return fareResultDetails;
    }

    public void setFareDetails(FareResultDetails fareDetails) {
        this.fareResultDetails = fareDetails;
    }

    public class FareResultDetails implements Serializable {
        @SerializedName("result")
        @Expose
        FareDetails fareDetails;

        public FareDetails getFareDetails() {
            return fareDetails;
        }

        public void setFareDetails(FareDetails fareDetails) {
            this.fareDetails = fareDetails;
        }
    }
    public class FareDetails implements Serializable {
        @SerializedName("fare_breakup")
        @Expose
        ArrayList<FareBreakup> fareBreakups;

        @SerializedName("total")
        @Expose
        int total;

        @SerializedName("no_child_seat")
        @Expose
        int totalChildSeat;

        @SerializedName("no_adult_seat")
        @Expose
        int totalAdultSeat;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalChildSeat() {
            return totalChildSeat;
        }

        public void setTotalChildSeat(int totalChildSeat) {
            this.totalChildSeat = totalChildSeat;
        }

        public int getTotalAdultSeat() {
            return totalAdultSeat;
        }

        public void setTotalAdultSeat(int totalAdultSeat) {
            this.totalAdultSeat = totalAdultSeat;
        }

        public ArrayList<FareBreakup> getFareBreakups() {
            return fareBreakups;
        }

        public void setFareBreakups(ArrayList<FareBreakup> fareBreakups) {
            this.fareBreakups = fareBreakups;
        }
    }
}
