package com.currentbooking.utilits.cb_api.responses;

import java.io.Serializable;

public class CCAvenueResponse implements Serializable {
    String bos_id;
    String fare;
    String msg;
    String service_charge;
    String status;
    String ticket_id;
    String ticket_number;
    String total_fare;

    public String getBos_id() {
        return bos_id;
    }

    public void setBos_id(String bos_id) {
        this.bos_id = bos_id;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getService_charge() {
        return service_charge;
    }

    public void setService_charge(String service_charge) {
        this.service_charge = service_charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(String total_fare) {
        this.total_fare = total_fare;
    }
}
