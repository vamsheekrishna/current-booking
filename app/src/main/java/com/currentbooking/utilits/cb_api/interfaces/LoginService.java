package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.BuildConfig;
import com.currentbooking.utilits.cb_api.responses.ForgotPasswordResponse;
import com.currentbooking.utilits.cb_api.responses.LoginResponse;
import com.currentbooking.utilits.cb_api.responses.RegistrationResponse;
import com.currentbooking.utilits.cb_api.responses.ResendOTPResponse;
import com.currentbooking.utilits.cb_api.responses.ResponseUpdateProfile;
import com.currentbooking.utilits.cb_api.responses.ValidateOTP;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST(BuildConfig.LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("mobileno") String username,
                              @Field("password") String password);

    @POST(BuildConfig.REGISTRATION)
    @FormUrlEncoded
    Call<RegistrationResponse> registration(@Field("first_name")  String fName,
                                            @Field("last_name") String lName,
                                            @Field("mobno") String mobile,
                                            @Field("email") String email,
                                            @Field("password") String password,
                                            @Field("confirm_password") String conformPassword);

    @POST(BuildConfig.VALIDATE_OTP)
    @FormUrlEncoded
    Call<ValidateOTP> validateOTP(@Field("mobileno") String mobile,
                                  @Field("otp") String otp);

    @POST(BuildConfig.FORGOT_PASSWORD)
    @FormUrlEncoded
    Call<ForgotPasswordResponse> forgotPassword(@Field("mobileno") String mobile);

    @POST(BuildConfig.RESEND_OTP)
    @FormUrlEncoded
    Call<ResendOTPResponse> resendOTP(@Field("mobileno") String mobile);

    @POST(BuildConfig.PROFILE_UPDATE)
    @FormUrlEncoded
    Call<ResponseUpdateProfile> updateProfile(@Field("user_id") String userID,
                                              @Field("first_name") String firstName,
                                              @Field("last_name") String lastName,
                                              @Field("gender") String gender,
                                              @Field("email") String email,
                                              @Field("address") String address,
                                              @Field("address2") String address2,
                                              @Field("pincode") String pincode,
                                              @Field("dob") String dob,
                                              @Field("image") String image,
                                              @Field("state") String state);
}