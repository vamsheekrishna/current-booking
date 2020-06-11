package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusObject {
    @SerializedName("route_no")
    @Expose
    String routeNumber;

    @SerializedName("source_stage_short_name")
    @Expose
    String sourceStageShortName;

    @SerializedName("source_stage_name")
    @Expose
    String sourceStageName;

    @SerializedName("destination_stage_short_name")
    @Expose
    String destinationStageShortName;

    @SerializedName("destination_stage_name")
    @Expose
    String destinationStageName;

    @SerializedName("BUS_TYPE_CD")
    @Expose
    String busTypeCD;

    @SerializedName("BUS_TYPE_NM")
    @Expose
    String busTypeNM;

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getSourceStageShortName() {
        return sourceStageShortName;
    }

    public void setSourceStageShortName(String sourceStageShortName) {
        this.sourceStageShortName = sourceStageShortName;
    }

    public String getSourceStageName() {
        return sourceStageName;
    }

    public void setSourceStageName(String sourceStageName) {
        this.sourceStageName = sourceStageName;
    }

    public String getDestinationStageShortName() {
        return destinationStageShortName;
    }

    public void setDestinationStageShortName(String destinationStageShortName) {
        this.destinationStageShortName = destinationStageShortName;
    }

    public String getDestinationStageName() {
        return destinationStageName;
    }

    public void setDestinationStageName(String destinationStageName) {
        this.destinationStageName = destinationStageName;
    }

    public String getBusTypeCD() {
        return busTypeCD;
    }

    public void setBusTypeCD(String busTypeCD) {
        this.busTypeCD = busTypeCD;
    }

    public String getBusTypeNM() {
        return busTypeNM;
    }

    public void setBusTypeNM(String busTypeNM) {
        this.busTypeNM = busTypeNM;
    }
}
