package com.currentbooking.ticketbooking;

import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;

public interface OnTicketBookingListener {
    void goToHome();
    void gotoSelectBus(String busOperatorName, String busType);

    void gotoConfirmTicket(String busType, GetFareResponse.FareDetails personsAddedList);
    void gotoPassengerDetails(String busType);

    void gotoPayment(GetFareResponse.FareDetails mFareDetails);
    void gotoTicketStatus(boolean ticketStatus, String passengerDetails, Object bookingDetails);
    void gotoTicketStatus(CCAvenueResponse ccAvenueResponse);
    void gotoOptionSelection(int listData);
    void gotoBusStopSelect(int i);
}
