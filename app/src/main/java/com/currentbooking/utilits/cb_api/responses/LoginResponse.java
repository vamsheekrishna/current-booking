package com.currentbooking.utilits.cb_api.responses;

import com.currentbooking.profile.ui.main.ProfileViewModel;
import com.currentbooking.utilits.cb_api.responses.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    LoginData loginData;

    public LoginData getData() {
        return loginData;
    }

    public void setData(LoginData data) {
        this.loginData = data;
    }

    public class LoginData{
        @SerializedName("result")
        @Expose
        ProfileModel profileModel;

        public ProfileModel getProfileModel() {
            return profileModel;
        }

        public void setProfileModel(ProfileModel profileViewModel) {
            this.profileModel = profileViewModel;
        }
    }
}
