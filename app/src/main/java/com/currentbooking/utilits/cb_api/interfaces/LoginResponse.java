package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.utilits.cb_api.responses.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public class LoginData{

    }
}
