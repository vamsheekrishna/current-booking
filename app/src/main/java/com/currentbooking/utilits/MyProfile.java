package com.currentbooking.utilits;

import com.currentbooking.utilits.cb_api.responses.ProfileModel;

public class MyProfile {
    private static MyProfile myProfile;

    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String mobileNumber;
    private String pinCode;
    private String profileImage;
    private String address1;
    private String address2;
    private String state;

    private MyProfile() {

    }
    public static MyProfile getInstance() {
        if(myProfile == null) {
            myProfile = new MyProfile();
        }
        return myProfile;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setData(ProfileModel profileModel) {
        firstName = profileModel.getFirstName();
        lastName = profileModel.getLastName();
        dob = profileModel.getDob();
        email = profileModel.getEmail();
        mobileNumber = profileModel.getMobileNumber();
        pinCode = profileModel.getPinCode();
        profileImage = profileModel.getProfileImage();
    }
}
