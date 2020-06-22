package com.currentbooking.ticketbooking;

import com.currentbooking.utilits.cb_api.responses.Concession;

import java.util.List;

public interface OnTicketBookingListener {
    void goToHome();
    void gotoSelectBus(String busOperatorName, String busType);

    void gotoConfirmTicket(String busType, List<Concession> personsAddedList);
    void gotoPassengerDetails(String busType);

    void gotoPayment();
    void gotoTicketStatus(boolean ticketStatus, String passengerDetails, Object bookingDetails);
    void gotoOptionSelection(int listData);
    void gotoBusStopSelect(int i);
}
