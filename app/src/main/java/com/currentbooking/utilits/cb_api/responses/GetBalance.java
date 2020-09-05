package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBalance extends BaseResponse{

    @SerializedName("result")
    @Expose
    private CCAvenueResponse result;



    public CCAvenueResponse getResult() {
        return result;
    }

    public void setResult(CCAvenueResponse result) {
        this.result = result;
    }

}

