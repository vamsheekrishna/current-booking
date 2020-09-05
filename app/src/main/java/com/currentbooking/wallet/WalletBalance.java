package com.currentbooking.wallet;

import com.currentbooking.utilits.cb_api.responses.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletBalance extends BaseResponse {

    @SerializedName("result")
    @Expose
    MyWalletBalance availableBalance;

    public MyWalletBalance getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(MyWalletBalance availableb) {
        this.availableBalance = availableb;
    }
}
