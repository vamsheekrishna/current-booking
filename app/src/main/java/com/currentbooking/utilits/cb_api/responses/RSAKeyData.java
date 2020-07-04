package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RSAKeyData {

    @SerializedName("rsa_key")
    @Expose
    String rsa_key;

    @SerializedName("merchant_id")
    @Expose
    String merchant_id;

    @SerializedName("order_id")
    @Expose
    String order_id;

    @SerializedName("access_code")
    @Expose
    String access_code;

    @SerializedName("redirect_url")
    @Expose
    String redirect_url;

    @SerializedName("cancel_url")
    @Expose
    String cancel_url;

    public String getRsa_key() {
        return rsa_key;
    }

    public void setRsa_key(String rsa_key) {
        this.rsa_key = rsa_key;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAccess_code() {
        return access_code;
    }

    public void setAccess_code(String access_code) {
        this.access_code = access_code;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }
}
