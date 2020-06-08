package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.BuildConfig;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST(BuildConfig.LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("Username") String username,
                              @Field("Password") String password);
}
