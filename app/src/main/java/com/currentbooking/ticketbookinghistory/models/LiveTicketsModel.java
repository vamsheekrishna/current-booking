package com.currentbooking.ticketbookinghistory.models;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 30/06/20.
 */
public class LiveTicketsModel implements Serializable {

    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
