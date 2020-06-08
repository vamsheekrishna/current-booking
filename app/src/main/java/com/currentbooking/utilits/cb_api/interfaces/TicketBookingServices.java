package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.BuildConfig;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TicketBookingServices {

    @GET(BuildConfig.OPERATOR_LIST)
    public Call<BusOperatorList> getBusOperators();
}
