package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Concession implements Serializable  {
    @SerializedName("concession_id")
    @Expose
    String concessionID;
    @SerializedName("concession_cd")
    @Expose
    String concessionCD;
    @SerializedName("concession_nm")
    @Expose
    String concessionNM;
    @SerializedName("child_permitted")
    @Expose
    String childPermitted;
    @SerializedName("adult_permitted")
    @Expose
    String adultPermitted;
    @SerializedName("category")
    @Expose
    String category;
    @SerializedName("is_parentconc")
    @Expose
    String isParentconc;
    @SerializedName("parent_conc_cd")
    @Expose
    String parentConcCD;
    @SerializedName("for_route_type")
    @Expose
    String forRouteType;
    @SerializedName("is_audit_trail")
    @Expose
    String isAuditTrail;
    @SerializedName("concession_order")
    @Expose
    String concessionOrder;
    @SerializedName("department_flag")
    @Expose
    String departmentFlag;
    @SerializedName("corporation_flag")
    @Expose
    String corporationFlag;
    @SerializedName("concession_devnagiri_nm")
    @Expose
    String concessionDevnagiriNM;
    @SerializedName("min_age_limit")
    @Expose
    String minAgeLimit;
    @SerializedName("max_age_limit")
    @Expose
    String maxAgeLimit;

    private String personType;
    private boolean isConcessionDetailsAdded = false;
    private int age;

    public String getConcessionID() {
        return concessionID;
    }

    public void setConcessionID(String concessionID) {
        this.concessionID = concessionID;
    }

    public String getConcessionCD() {
        return concessionCD;
    }

    public void setConcessionCD(String concessionCD) {
        this.concessionCD = concessionCD;
    }

    public String getConcessionNM() {
        return concessionNM;
    }

    public void setConcessionNM(String concessionNM) {
        this.concessionNM = concessionNM;
    }

    public String getChildPermitted() {
        return childPermitted;
    }

    public void setChildPermitted(String childPermitted) {
        this.childPermitted = childPermitted;
    }

    public String getAdultPermitted() {
        return adultPermitted;
    }

    public void setAdultPermitted(String adultPermitted) {
        this.adultPermitted = adultPermitted;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsParentconc() {
        return isParentconc;
    }

    public void setIsParentconc(String isParentconc) {
        this.isParentconc = isParentconc;
    }

    public String getParentConcCD() {
        return parentConcCD;
    }

    public void setParentConcCD(String parentConcCD) {
        this.parentConcCD = parentConcCD;
    }

    public String getForRouteType() {
        return forRouteType;
    }

    public void setForRouteType(String forRouteType) {
        this.forRouteType = forRouteType;
    }

    public String getIsAuditTrail() {
        return isAuditTrail;
    }

    public void setIsAuditTrail(String isAuditTrail) {
        this.isAuditTrail = isAuditTrail;
    }

    public String getConcessionOrder() {
        return concessionOrder;
    }

    public void setConcessionOrder(String concessionOrder) {
        this.concessionOrder = concessionOrder;
    }

    public String getDepartmentFlag() {
        return departmentFlag;
    }

    public void setDepartmentFlag(String departmentFlag) {
        this.departmentFlag = departmentFlag;
    }

    public String getCorporationFlag() {
        return corporationFlag;
    }

    public void setCorporationFlag(String corporationFlag) {
        this.corporationFlag = corporationFlag;
    }

    public String getConcessionDevnagiriNM() {
        return concessionDevnagiriNM;
    }

    public void setConcessionDevnagiriNM(String concessionDevnagiriNM) {
        this.concessionDevnagiriNM = concessionDevnagiriNM;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public boolean isConcessionDetailsAdded() {
        return isConcessionDetailsAdded;
    }

    public void setConcessionDetailsAdded(boolean concessionDetailsAdded) {
        isConcessionDetailsAdded = concessionDetailsAdded;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMinAgeLimit() {
        return minAgeLimit;
    }

    public void setMinAgeLimit(String minAgeLimit) {
        this.minAgeLimit = minAgeLimit;
    }

    public String getMaxAgeLimit() {
        return maxAgeLimit;
    }

    public void setMaxAgeLimit(String maxAgeLimit) {
        this.maxAgeLimit = maxAgeLimit;
    }
}
