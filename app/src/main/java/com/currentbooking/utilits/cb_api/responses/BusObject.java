package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusObject implements Serializable {

    @SerializedName("origin_stop_cd")
    @Expose
    private String originStopCd;
    @SerializedName("origin_stop_name")
    @Expose
    private String originStopName;
    @SerializedName("origin_date_time")
    @Expose
    private String originDateTime;
    @SerializedName("last_stop_cd")
    @Expose
    private String lastStopCd;
    @SerializedName("last_stop_name")
    @Expose
    private String lastStopName;
    @SerializedName("last_stop_date_time")
    @Expose
    private String lastStopDateTime;
    @SerializedName("req_from_stop_cd")
    @Expose
    private String reqFromStopCd;
    @SerializedName("req_from_stop_nm")
    @Expose
    private String reqFromStopNm;
    @SerializedName("req_from_date_time")
    @Expose
    private String reqFromDateTime;
    @SerializedName("req_till_stop_cd")
    @Expose
    private String reqTillStopCd;
    @SerializedName("req_till_stop_nm")
    @Expose
    private String reqTillStopNm;
    @SerializedName("req_till_date_time")
    @Expose
    private String reqTillDateTime;
    @SerializedName("depot_nm")
    @Expose
    private String depotNm;
    @SerializedName("bus_service_no")
    @Expose
    private String busServiceNo;
    @SerializedName("fare_amt")
    @Expose
    private String fareAmt;

    public String getOriginStopCd() {
        return originStopCd;
    }

    public void setOriginStopCd(String originStopCd) {
        this.originStopCd = originStopCd;
    }

    public String getOriginStopName() {
        return originStopName;
    }

    public void setOriginStopName(String originStopName) {
        this.originStopName = originStopName;
    }

    public String getOriginDateTime() {
        return originDateTime;
    }

    public void setOriginDateTime(String originDateTime) {
        this.originDateTime = originDateTime;
    }

    public String getLastStopCd() {
        return lastStopCd;
    }

    public void setLastStopCd(String lastStopCd) {
        this.lastStopCd = lastStopCd;
    }

    public String getLastStopName() {
        return lastStopName;
    }

    public void setLastStopName(String lastStopName) {
        this.lastStopName = lastStopName;
    }

    public String getLastStopDateTime() {
        return lastStopDateTime;
    }

    public void setLastStopDateTime(String lastStopDateTime) {
        this.lastStopDateTime = lastStopDateTime;
    }

    public String getReqFromStopCd() {
        return reqFromStopCd;
    }

    public void setReqFromStopCd(String reqFromStopCd) {
        this.reqFromStopCd = reqFromStopCd;
    }

    public String getReqFromStopNm() {
        return reqFromStopNm;
    }

    public void setReqFromStopNm(String reqFromStopNm) {
        this.reqFromStopNm = reqFromStopNm;
    }

    public String getReqFromDateTime() {
        return reqFromDateTime;
    }

    public void setReqFromDateTime(String reqFromDateTime) {
        this.reqFromDateTime = reqFromDateTime;
    }

    public String getReqTillStopCd() {
        return reqTillStopCd;
    }

    public void setReqTillStopCd(String reqTillStopCd) {
        this.reqTillStopCd = reqTillStopCd;
    }

    public String getReqTillStopNm() {
        return reqTillStopNm;
    }

    public void setReqTillStopNm(String reqTillStopNm) {
        this.reqTillStopNm = reqTillStopNm;
    }

    public String getReqTillDateTime() {
        return reqTillDateTime;
    }

    public void setReqTillDateTime(String reqTillDateTime) {
        this.reqTillDateTime = reqTillDateTime;
    }

    public String getDepotNm() {
        return depotNm;
    }

    public void setDepotNm(String depotNm) {
        this.depotNm = depotNm;
    }

    public String getBusServiceNo() {
        return busServiceNo;
    }

    public void setBusServiceNo(String busServiceNo) {
        this.busServiceNo = busServiceNo;
    }

    public String getFareAmt() {
        return fareAmt;
    }

    public void setFareAmt(String fareAmt) {
        this.fareAmt = fareAmt;
    }
}
