package com.currentbooking.utilits.cb_api.responses;

import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TodayTickets extends BaseResponse {

    @SerializedName("result")
    @Expose
    ArrayList<MyTicketInfo> availableTickets;

    public ArrayList<MyTicketInfo> getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(ArrayList<MyTicketInfo> availableTickets) {
        this.availableTickets = availableTickets;
    }
}
