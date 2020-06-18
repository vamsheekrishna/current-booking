package com.currentbooking.ticketbooking;

import com.currentbooking.utilits.cb_api.responses.BusObject;

public interface OnTicketBookingListener {
    void goToHome();
    void goToSelectBus();

    void goToConfirmTicket(String busOperatorName, BusObject busObject);

    void goToPayment();
    void goToTicketStatus();
    void goToOptionSelection(int listData);
    void goToBusStopSelect(int i);
}
