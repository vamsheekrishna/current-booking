package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.BuildConfig;
import com.currentbooking.utilits.cb_api.responses.LoginResponse;
import com.currentbooking.utilits.cb_api.responses.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST(BuildConfig.LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("Username") String username,
                              @Field("Password") String password);

    @POST(BuildConfig.REGISTRATION)
    @FormUrlEncoded
    Call<RegistrationResponse> registration(@Field("first_name")  String fName,
                                            @Field("last_name") String lName,
                                            @Field("username") String userID,
                                            @Field("mobno") String mobile,
                                            @Field("email") String email,
                                            @Field("password") String password,
                                            @Field("confirm_password") String conformPassword);
}