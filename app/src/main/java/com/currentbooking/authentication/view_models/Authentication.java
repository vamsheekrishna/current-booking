package com.currentbooking.authentication.view_models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;

public class Authentication extends ViewModel {

    String TAG = Authentication.class.getSimpleName();
    private MutableLiveData<String> mFirstName = new MutableLiveData<>();
    private MutableLiveData<String> mLastName = new MutableLiveData<>();
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    private MutableLiveData<String> mMobile = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();
    private MutableLiveData<String> mConfirmPassword = new MutableLiveData<>();

    private final LoginService loginService;


    public Authentication() {
        loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
    }


    public String getFirstName() {
        return mFirstName.getValue();
    }
    public String getLastName() {
        return mLastName.getValue();
    }
    public String getEmail() {
        return mEmail.getValue();
    }
    public String getUserName() {
        return mUserName.getValue();
    }
    public  String getMobileNumber() {
        return mMobile.getValue();
    }
    public String getPassword() {
        return mPassword.getValue();
    }
    public String getConformationPassword() {
        return mConfirmPassword.getValue();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "on cleared called");
    }

}
