package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusType {
    @SerializedName("BUS_TYPE_ID")
    @Expose
    String busTypeID;

    @SerializedName("BUS_TYPE_CD")
    @Expose
    String busTypeCD;

    @SerializedName("BUS_TYPE_NM")
    @Expose
    String busTypeName;

    @SerializedName("FOR_ROUTE_TYPE")
    @Expose
    String forRouteType;

    @SerializedName("PREFERENCE")
    @Expose
    String preference;

    @SerializedName("RES_CHARGE_ETIM")
    @Expose
    String resChargeETIM;

    @SerializedName("BUS_DEVNAGIRI_NM")
    @Expose
    String busDevnagiriName;

    public String getBusTypeID() {
        return busTypeID;
    }

    public void setBusTypeID(String busTypeID) {
        this.busTypeID = busTypeID;
    }

    public String getBusTypeCD() {
        return busTypeCD;
    }

    public void setBusTypeCD(String busTypeCD) {
        this.busTypeCD = busTypeCD;
    }

    public String getBusTypeName() {
        return busTypeName;
    }

    public void setBusTypeName(String busTypeName) {
        this.busTypeName = busTypeName;
    }

    public String getForRouteType() {
        return forRouteType;
    }

    public void setForRouteType(String forRouteType) {
        this.forRouteType = forRouteType;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getResChargeETIM() {
        return resChargeETIM;
    }

    public void setResChargeETIM(String resChargeETIM) {
        this.resChargeETIM = resChargeETIM;
    }

    public String getBusDevnagiriName() {
        return busDevnagiriName;
    }

    public void setBusDevnagiriName(String busDevnagiriName) {
        this.busDevnagiriName = busDevnagiriName;
    }
}
