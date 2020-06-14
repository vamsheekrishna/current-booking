package com.currentbooking.profile.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> firstName;
    private MutableLiveData<String> lastName;
    private MutableLiveData<String> email;
    private MutableLiveData<String> address1;
    private MutableLiveData<String> address2;
    private MutableLiveData<String> pinCode;
    private MutableLiveData<String> state;
    private MutableLiveData<Long> dob;
    private MutableLiveData<String> mobileNumber;

    public MutableLiveData<String> getState() {
        if(null == state) {
            state = new MutableLiveData<>();
        }
        return state;
    }

    public void setState(MutableLiveData<String> state) {
        this.state = state;
    }

    public MutableLiveData<String> getEmail() {
        if(null == email) {
            email = new MutableLiveData<>();
        }
        return email;
    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
    }

    public MutableLiveData<String> getFirstName() {
        if(null == firstName) {
            firstName = new MutableLiveData<>();
        }
        return firstName;
    }

    public void setFirstName(MutableLiveData<String> firstName) {
        this.firstName = firstName;
    }

    public MutableLiveData<String> getLastName() {
        if(null == lastName) {
            lastName = new MutableLiveData<>();
        }
        return lastName;
    }

    public void setLastName(MutableLiveData<String> lastName) {
        this.lastName = lastName;
    }

    public MutableLiveData<String> getAddress1() {
        if(null == address1) {
            address1 = new MutableLiveData<>();
        }
        return address1;
    }

    public void setAddress1(MutableLiveData<String> address1) {
        this.address1 = address1;
    }

    public MutableLiveData<String> getAddress2() {
        if(null == address2) {
            address2 = new MutableLiveData<>();
        }
        return address2;
    }

    public void setAddress2(MutableLiveData<String> address2) {
        this.address2 = address2;
    }

    public MutableLiveData<String> getPinCode() {
        if(null == pinCode) {
            pinCode = new MutableLiveData<>();
        }
        return pinCode;
    }

    public void setPinCode(MutableLiveData<String> pinCode) {
        this.pinCode = pinCode;
    }

    public MutableLiveData<Long> getDob() {
        if(null == dob) {
            dob = new MutableLiveData<>();
        }
        return dob;
    }

    public void setDob(MutableLiveData<Long> dob) {
        this.dob = dob;
    }

    public MutableLiveData<String> getMobileNumber() {
        if(null == mobileNumber) {
            mobileNumber = new MutableLiveData<>();
        }
        return mobileNumber;
    }

    public void setMobileNumber(MutableLiveData<String> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}