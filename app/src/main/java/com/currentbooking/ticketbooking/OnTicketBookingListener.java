package com.currentbooking.ticketbooking;

public interface OnTicketBookingListener {
    void goToHome();
    void goToSelectBus();
    void goToConformTicket();
    void goToPayment();
    void goToTicketStatus();
    void goToOptionSelection(int listData);
    void goToBusStopSelect(int i);
}
