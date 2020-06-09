package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.BuildConfig;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;
import com.currentbooking.utilits.cb_api.responses.BusTypeList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TicketBookingServices {

    @POST(BuildConfig.OPERATOR_LIST)
    Call<BusOperatorList> getBusOperators();

    @POST(BuildConfig.BUS_TYPE)
    @FormUrlEncoded
    Call<BusTypeList> getBusTypes(@Field("operator") String operator);
}
