package com.currentbooking.utilits.cb_api.responses;

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

    public class AvailableTickets {
        @SerializedName("bos_id")
        @Expose
        String bosID;

        @SerializedName("ticket_no")
        @Expose
        String ticket_no;

        @SerializedName("order_id")
        @Expose
        String order_id;

        @SerializedName("ticket_status")
        @Expose
        String ticket_status;

        @SerializedName("operator_name")
        @Expose
        String operator_name;

        @SerializedName("bus_service_no")
        @Expose
        String bus_service_no;

        @SerializedName("to_stop")
        @Expose
        String to_stop;
        @SerializedName("from_stop")
        @Expose
        String from_stop;
        @SerializedName("start_time")
        @Expose
        String start_time;
        @SerializedName("drop_time")
        @Expose
        String drop_time;
        @SerializedName("hours")
        @Expose
        String hours;
        @SerializedName("total")
        @Expose
        String total;
        @SerializedName("no_adult_seat")
        @Expose
        String no_adult_seat;
        @SerializedName("no_child_seat")
        @Expose
        String no_child_seat;
        @SerializedName("no_con_seat")
        @Expose
        String no_con_seat;
        @SerializedName("passenger_details")
        @Expose
        ArrayList<PassengerDetail> passenger_detail;

        public String getBosID() {
            return bosID;
        }

        public void setBosID(String bosID) {
            this.bosID = bosID;
        }

        public String getTicket_no() {
            return ticket_no;
        }

        public void setTicket_no(String ticket_no) {
            this.ticket_no = ticket_no;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getTicket_status() {
            return ticket_status;
        }

        public void setTicket_status(String ticket_status) {
            this.ticket_status = ticket_status;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getBus_service_no() {
            return bus_service_no;
        }

        public void setBus_service_no(String bus_service_no) {
            this.bus_service_no = bus_service_no;
        }

        public String getTo_stop() {
            return to_stop;
        }

        public void setTo_stop(String to_stop) {
            this.to_stop = to_stop;
        }

        public String getFrom_stop() {
            return from_stop;
        }

        public void setFrom_stop(String from_stop) {
            this.from_stop = from_stop;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getDrop_time() {
            return drop_time;
        }

        public void setDrop_time(String drop_time) {
            this.drop_time = drop_time;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getNo_adult_seat() {
            return no_adult_seat;
        }

        public void setNo_adult_seat(String no_adult_seat) {
            this.no_adult_seat = no_adult_seat;
        }

        public String getNo_child_seat() {
            return no_child_seat;
        }

        public void setNo_child_seat(String no_child_seat) {
            this.no_child_seat = no_child_seat;
        }

        public String getNo_con_seat() {
            return no_con_seat;
        }

        public void setNo_con_seat(String no_con_seat) {
            this.no_con_seat = no_con_seat;
        }

        public ArrayList<PassengerDetail> getPassenger_detail() {
            return passenger_detail;
        }

        public void setPassenger_detail(ArrayList<PassengerDetail> passenger_detail) {
            this.passenger_detail = passenger_detail;
        }
    }
    public class PassengerDetail {

        @SerializedName("first_name")
        @Expose
        String firstName;

        String type = "Adult";

        @SerializedName("age")
        @Expose
        String age;
        @SerializedName("basic_adult_amt")
        @Expose
        String basicAmt;
        @SerializedName("concession_cd")
        @Expose
        String concessionCD;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBasicAmt() {
            return basicAmt;
        }

        public void setBasicAmt(String basicAmt) {
            this.basicAmt = basicAmt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getConcessionCD() {
            return concessionCD;
        }

        public void setConcessionCD(String concessionCD) {
            this.concessionCD = concessionCD;
        }
    }
}
