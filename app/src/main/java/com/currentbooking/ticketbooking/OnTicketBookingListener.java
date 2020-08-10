package com.currentbooking.ticketbooking;

import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;

public interface OnTicketBookingListener extends BaseListener {
    void goToHome();
    void gotoSelectBus(String busOperatorName, String busType);

    void gotoConfirmTicket(String busType, GetFareResponse.FareDetails personsAddedList, String passengerDetails);
    void gotoPassengerDetails(String busType);

    void gotoPayment(String passengerDetails, GetFareResponse.FareDetails mFareDetails);
    void gotoTicketStatus(String passengerDetails, CCAvenueResponse bookingDetails);
    //void gotoTicketStatus(CCAvenueResponse ccAvenueResponse);
    void gotoOptionSelection(int listData, String title);
    void gotoBusStopSelect(int i, double latitude, double longitude);
}
