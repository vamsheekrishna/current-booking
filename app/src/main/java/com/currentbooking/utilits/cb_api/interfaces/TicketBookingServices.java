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
import com.currentbooking.utilits.cb_api.responses.RSAKeyResponse;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.cb_api.responses.UpdateTicketStatus;
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
    @POST(BuildConfig.GET_RSA_KEY)
    @FormUrlEncoded
    Call<RSAKeyResponse> getRSAKey(@Field("bus_operator") String busOperator,
                                   @Field("bus_details") String busDetails,
                                   @Field("passenger_details") String passengerDetails,
                                   @Field("breakup") String fareDetails,
                                   @Field("user_id") String userID);

    @POST(BuildConfig.GET_TODAYS_TICKETS)
    @FormUrlEncoded
    Call<TodayTickets> getCurrentBookingTicket(@Field("date") String date,
                                               @Field("user_id") String userID, @Field("ticket_id") String ticketID);

    @POST(BuildConfig.VALIDATE_TICKET)
    @FormUrlEncoded
    Call<UpdateTicketStatus> updateTicketStatus(
            @Field("waybill_no") String wayBillNo,
            @Field("depot_name") String depot_name,
            @Field("depot_code") String depot_code,
            @Field("trip_no") String trip_no,
            @Field("route_no") String route_no,
            @Field("bus_type") String bus_type,
            @Field("bus_time") String bus_time,
            @Field("conductor_id") String conductor_id,
            @Field("conductor_name") String conductor_name,
            @Field("status") String status,
            @Field("ticket_id") String ticketID,
            @Field("operator") String operator,
            @Field("etim_no") String etim_no,
            @Field("bus_no") String bus_no,
            @Field("machine_no") String machine_no,
            @Field("bus_service_id") String bus_service_id
    );

    @POST(BuildConfig.AVAILABLE_BUS_STOPS)
    @FormUrlEncoded
    Call<BusStopResponse> getNearByStopsList(@Field("latitude") double latitude,
                                         @Field("longitude") double longitude);
}
