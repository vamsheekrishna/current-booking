package com.currentbooking.ticketbookinghistory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Satya Seshu on 30/06/20.
 */

public class TicketViewModel implements Serializable {

    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("ticket_no")
    @Expose
    String ticketNo;
    @SerializedName("ticket_status")
    @Expose
    String ticketStatus;
    @SerializedName("operator_name")
    @Expose
    String operatorName;
    @SerializedName("from_stop")
    @Expose
    String fromStop;
    @SerializedName("to_stop")
    @Expose
    String toStop;
    @SerializedName("passenger_details")
    @Expose
    ArrayList<PassengerDetails> passengerDetails;

    @SerializedName("fare")
    @Expose
    String fare;

    @SerializedName("service_charge")
    @Expose
    String serviceCharge;

    @SerializedName("total_fare")
    @Expose
    String totalFare;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getFromStop() {
        return fromStop;
    }

    public void setFromStop(String fromStop) {
        this.fromStop = fromStop;
    }

    public String getToStop() {
        return toStop;
    }

    public void setToStop(String toStop) {
        this.toStop = toStop;
    }

    public ArrayList<PassengerDetails> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(ArrayList<PassengerDetails> passengerDetails) {
        this.passengerDetails = passengerDetails;
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

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    class PassengerDetails {
        @SerializedName("name")
        @Expose
        String name;
        @SerializedName("age")
        @Expose
        String age;
        @SerializedName("concession")
        @Expose
        String concession;
        @SerializedName("fare")
        @Expose
        String fare;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getConcession() {
            return concession;
        }

        public void setConcession(String concession) {
            this.concession = concession;
        }

        public String getFare() {
            return fare;
        }

        public void setFare(String fare) {
            this.fare = fare;
        }
    }
}
