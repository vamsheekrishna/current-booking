package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusOperator {
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

    @SerializedName("created_by")
    @Expose
    private String createdBy;

    @SerializedName("created_date")
    @Expose
    private String createdDate = "2019-12-31 10:47:43";

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

    public String getCreatedBy() {
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
    }
}