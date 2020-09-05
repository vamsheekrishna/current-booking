package com.currentbooking.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 14/07/20.
 */
public class MyWalletBalance implements Serializable {

    @SerializedName("user_id")
    @Expose
    String User_id;

    @SerializedName("balance")
    @Expose
    String wallet_balance;



    @SerializedName("limit")
    @Expose
    String limit;

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String bosID) {
        this.User_id = bosID;
    }

    public String getwallet_balance() {
        return wallet_balance;
    }

    public void setwallet_balance(String wallet_balance) {
        this.wallet_balance = wallet_balance;
    }
    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
