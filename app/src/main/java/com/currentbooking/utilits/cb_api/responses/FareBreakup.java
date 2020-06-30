package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FareBreakup {
    @SerializedName("lable")
    @Expose
    String lable;

    @SerializedName("val")
    @Expose
    String val;

    public String getLabel() {
        return lable;
    }

    public void setLabel(String lable) {
        this.lable = lable;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
