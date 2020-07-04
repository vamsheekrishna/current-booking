package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RSAKeyResponse extends BaseResponse {

    @SerializedName("result")
    @Expose
    private RSAKeyData data;

    public RSAKeyData getData() {
        return data;
    }

    public void setData(RSAKeyData data) {
        this.data = data;
    }
}
