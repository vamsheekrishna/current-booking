package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusOperator implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("opertor_name")
    @Expose
    private String opertorName;

    @SerializedName("operator_code")
    @Expose
    private String operatorCode;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("adult_min_age")
    @Expose
    private String adultMinAge;

    @SerializedName("adult_max_age")
    @Expose
    private String adultMaxAge;

    @SerializedName("child_min_age")
    @Expose
    private String childMinAge;

    @SerializedName("child_max_age")
    @Expose
    private String childMaxAge;

    @SerializedName("max_ticket")
    @Expose
    private String maxTicket;

    @SerializedName("valid_till")
    @Expose
    private String validTill;

    /*    @SerializedName("created_by")
    @Expose
    private String createdBy;*/

/*    @SerializedName("created_date")
    @Expose
    private String createdDate = "2019-12-31 10:47:43";*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpertorName() {
        return opertorName;
    }

    public void setOpertorName(String opertorName) {
        this.opertorName = opertorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdultMinAge() {
        return adultMinAge;
    }

    public void setAdultMinAge(String adultMinAge) {
        this.adultMinAge = adultMinAge;
    }

    public String getAdultMaxAge() {
        return adultMaxAge;
    }

    public void setAdultMaxAge(String adultMaxAge) {
        this.adultMaxAge = adultMaxAge;
    }

    public String getChildMinAge() {
        return childMinAge;
    }

    public void setChildMinAge(String childMinAge) {
        this.childMinAge = childMinAge;
    }

    public String getChildMaxAge() {
        return childMaxAge;
    }

    public void setChildMaxAge(String childMaxAge) {
        this.childMaxAge = childMaxAge;
    }

    public String getMaxTicket() {
        return maxTicket;
    }

    public void setMaxTicket(String maxTicket) {
        this.maxTicket = maxTicket;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    /*   public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }*/
}
