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


    String getFirstName() {
        return mFirstName.getValue();
    }
    String getLastName() {
        return mLastName.getValue();
    }
    String getEmail() {
        return mEmail.getValue();
    }
    String getUserName() {
        return mUserName.getValue();
    }
    String getMobileNumber() {
        return mMobile.getValue();
    }
    String getPassword() {
        return mPassword.getValue();
    }
    String getConformationPassword() {
        return mConfirmPassword.getValue();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "on cleared called");
    }

}
