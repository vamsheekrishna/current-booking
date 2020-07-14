package com.currentbooking.utilits.cb_api.responses;

import com.currentbooking.ticketbookinghistory.models.AvailableTickets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TodayTickets extends BaseResponse {

    @SerializedName("result")
    @Expose
    ArrayList<AvailableTickets> availableTickets;

    public ArrayList<AvailableTickets> getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(ArrayList<AvailableTickets> availableTickets) {
        this.availableTickets = availableTickets;
    }
}
