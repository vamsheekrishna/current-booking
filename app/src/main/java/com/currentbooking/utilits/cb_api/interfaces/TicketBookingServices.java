package com.currentbooking.utilits.cb_api.interfaces;

import com.currentbooking.BuildConfig;
import com.currentbooking.utilits.cb_api.responses.AvailableBusList;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.cb_api.responses.BusStopResponse;
import com.currentbooking.utilits.cb_api.responses.BusTypeList;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.cb_api.responses.ConcessionListResponse;
import com.currentbooking.utilits.cb_api.responses.ConcessionRatesListResponse;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

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

    Call<BusStopObject> getBusPoints(String operatorName, String busTypeID);

    @POST(BuildConfig.AVAILABLE_BUS_LIST)
    @FormUrlEncoded
    Call<AvailableBusList> getAvailableBusList(@Field("operator") String butService,
                                               @Field("bus_type_cd") String busTypeCD,
                                               @Field("from") String from,
                                               @Field("to") String to);
    @POST(BuildConfig.AVAILABLE_BUS_STOPS)
    @FormUrlEncoded
    Call<BusStopResponse> getBusStopList(@Field("operator") String operator,
                                         @Field("stopname") String stopname);

    @POST(BuildConfig.CONCESSION_LIST)
    @FormUrlEncoded
    Call<ConcessionListResponse> getConcessionList(@Field("operator") String operator);

    @POST(BuildConfig.CONCESSION_RATE_LIST)
    @FormUrlEncoded
    Call<ConcessionRatesListResponse> getConcessionRatesList(@Field("operator") String operator);


    @POST(BuildConfig.GET_FARE)
    @FormUrlEncoded
    Call<GetFareResponse> getFare(@Field("operator") String operator,
                                  @Field("date") String date,
                                  @Field("from") String from,
                                  @Field("to") String to,
                                  @Field("bus_type_cd") String busTypeCD,
                                  @Field("bus_service_no") String busServiceNO,
                                  @Field("passenger_details") String result);
}
