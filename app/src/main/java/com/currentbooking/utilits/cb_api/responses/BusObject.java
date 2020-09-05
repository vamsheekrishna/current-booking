package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusObject implements Serializable {

    @SerializedName("route_no")
    @Expose
    private String routeNO;
    @SerializedName("bus_type_cd")
    @Expose
    private String busTypeCD;
    @SerializedName("bus_type_name")
    @Expose
    private String bus_type_name;
    @SerializedName("origin_stop_cd")
    @Expose
    private String originStopCD;
    @SerializedName("origin_stop_name")
    @Expose
    private String originStopName;
    @SerializedName("origin_stop_dev_nm")
    @Expose
    private String originStopDevNM;
    @SerializedName("origin_date_time")
    @Expose
    private String originDateTime;
    @SerializedName("last_stop_cd")
    @Expose
    private String lastStopCD;
    @SerializedName("lastStopName")
    @Expose
    private String lastStopDevNM;
    @SerializedName("last_stop_date_time")
    @Expose
    private String lastStopDateTime;
    @SerializedName("req_from_stop_cd")
    @Expose
    private String reqFromStopCD;
    @SerializedName("req_from_stop_dev_nm")
    @Expose
    private String reqFromStopDevNM;
    @SerializedName("req_from_stop_nm")
    @Expose
    private String reqFromStopNM;
    @SerializedName("req_from_date_time")
    @Expose
    private String reqFromDateTime;
    @SerializedName("req_till_stop_cd")
    @Expose
    private String reqTillStopCD;
    @SerializedName("req_till_stop_nm")
    @Expose
    private String reqTillStopNM;
    @SerializedName("req_till_stop_dev_nm")
    @Expose
    private String reqTillStopDevNM;
    @SerializedName("req_till_date_time")
    @Expose
    private String reqTillDateTime;
    @SerializedName("depot_nm")
    @Expose
    private String depotNM;
    @SerializedName("bus_service_no")
    @Expose
    private String busServiceNO;
    @SerializedName("fare_amt")
    @Expose
    private String fareAmt;

    public String getRouteNO() {
        return routeNO;
    }

    public void setRouteNO(String routeNO) {
        this.routeNO = routeNO;
    }

    public String getBusTypeCD() {
        return busTypeCD;
    }

    public void setBusTypeCD(String busTypeCD) {
        this.busTypeCD = busTypeCD;
    }

    public String getOriginStopCD() {
        return originStopCD;
    }

    public void setOriginStopCD(String originStopCD) {
        this.originStopCD = originStopCD;
    }

    public String getOriginStopName() {
        return originStopName;
    }

    public void setOriginStopName(String originStopName) {
        this.originStopName = originStopName;
    }

    public String getOriginStopDevNM() {
        return originStopDevNM;
    }

    public void setOriginStopDevNM(String originStopDevNM) {
        this.originStopDevNM = originStopDevNM;
    }

    public String getOriginDateTime() {
        return originDateTime;
    }

    public void setOriginDateTime(String originDateTime) {
        this.originDateTime = originDateTime;
    }

    public String getLastStopCD() {
        return lastStopCD;
    }

    public void setLastStopCD(String lastStopCD) {
        this.lastStopCD = lastStopCD;
    }

    public String getLastStopDevNM() {
        return lastStopDevNM;
    }

    public void setLastStopDevNM(String lastStopDevNM) {
        this.lastStopDevNM = lastStopDevNM;
    }

    public String getLastStopDateTime() {
        return lastStopDateTime;
    }

    public void setLastStopDateTime(String lastStopDateTime) {
        this.lastStopDateTime = lastStopDateTime;
    }

    public String getReqFromStopCD() {
        return reqFromStopCD;
    }

    public void setReqFromStopCD(String reqFromStopCD) {
        this.reqFromStopCD = reqFromStopCD;
    }

    public String getReqFromStopDevNM() {
        return reqFromStopDevNM;
    }

    public void setReqFromStopDevNM(String reqFromStopDevNM) {
        this.reqFromStopDevNM = reqFromStopDevNM;
    }

    public String getReqFromStopNM() {
        return reqFromStopNM;
    }

    public void setReqFromStopNM(String reqFromStopNM) {
        this.reqFromStopNM = reqFromStopNM;
    }

    public String getReqFromDateTime() {
        return reqFromDateTime;
    }

    public void setReqFromDateTime(String reqFromDateTime) {
        this.reqFromDateTime = reqFromDateTime;
    }

    public String getReqTillStopCD() {
        return reqTillStopCD;
    }

    public void setReqTillStopCD(String reqTillStopCD) {
        this.reqTillStopCD = reqTillStopCD;
    }

    public String getReqTillStopNM() {
        return reqTillStopNM;
    }

    public void setReqTillStopNM(String reqTillStopNM) {
        this.reqTillStopNM = reqTillStopNM;
    }

    public String getReqTillStopDevNM() {
        return reqTillStopDevNM;
    }

    public void setReqTillStopDevNM(String reqTillStopDevNM) {
        this.reqTillStopDevNM = reqTillStopDevNM;
    }

    public String getReqTillDateTime() {
        return reqTillDateTime;
    }

    public void setReqTillDateTime(String reqTillDateTime) {
        this.reqTillDateTime = reqTillDateTime;
    }

    public String getDepotNM() {
        return depotNM;
    }

    public void setDepotNM(String depotNM) {
        this.depotNM = depotNM;
    }

    public String getBusServiceNO() {
        return busServiceNO;
    }

    public void setBusServiceNO(String busServiceNO) {
        this.busServiceNO = busServiceNO;
    }

    public String getFareAmt() {
        return fareAmt;
    }

    public void setFareAmt(String fareAmt) {
        this.fareAmt = fareAmt;
    }

    public String getBus_type_name() {
        return bus_type_name;
    }

    public void setBus_type_name(String bus_type_name) {
        this.bus_type_name = bus_type_name;
    }
}
