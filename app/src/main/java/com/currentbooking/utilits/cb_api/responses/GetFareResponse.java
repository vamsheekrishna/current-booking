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

    public static class FareResultDetails implements Serializable {
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

    public static class FareDetails implements Serializable {

        String passengerDetails;
        @SerializedName("fare_breakup")
        @Expose
        ArrayList<FareBreakup> fareBreakups;

        @SerializedName("breakup")
        @Expose
        String breakup;

        @SerializedName("fare")
        @Expose
        String fare;

        @SerializedName("total")
        @Expose
        int total;

        @SerializedName("service_charge")
        @Expose
        String serviceCharge;

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

        public String getBreakup() {
            return breakup;
        }

        public void setBreakup(String breakup) {
            this.breakup = breakup;
        }

        public String getFare() {
            return fare;
        }

        public void setFare(String fare) {
            this.fare = fare;
        }

        public String getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(String serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public String getPassengerDetails() {
            return passengerDetails;
        }

        public void setPassengerDetails(String passengerDetails) {
            this.passengerDetails = passengerDetails;
        }
    }
}
