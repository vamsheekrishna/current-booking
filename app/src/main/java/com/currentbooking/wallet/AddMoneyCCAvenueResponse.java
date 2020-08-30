package com.currentbooking.wallet;

import java.io.Serializable;

public class AddMoneyCCAvenueResponse implements Serializable {
    String wallet_id;
    String fare;
    String msg;
    String updated_balance;
    String status;


    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUpdated_balance() {
        return updated_balance;
    }

    public void setUpdated_balance(String updated_balance) {
        this.updated_balance = updated_balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
