package com.currentbooking.ticketbookinghistory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 14/07/20.
 */
public class PassengerDetailsModel implements Serializable {

    @SerializedName("first_name")
    @Expose
    String firstName;

    String type = "Adult";

    @SerializedName("age")
    @Expose
    String age;
    @SerializedName("basic_adult_amt")
    @Expose
    String basicAmt;
    @SerializedName("concession_cd")
    @Expose
    String concessionCD;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBasicAmt() {
        return basicAmt;
    }

    public void setBasicAmt(String basicAmt) {
        this.basicAmt = basicAmt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConcessionCD() {
        return concessionCD;
    }

    public void setConcessionCD(String concessionCD) {
        this.concessionCD = concessionCD;
    }
}
