package com.currentbooking.ticketbookinghistory;

import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;

public interface OnTicketBookingHistoryListener {
    void showLiveTickets();
    void viewTicket(MyTicketInfo liveTicketsModel);

    void generateQRCode(MyTicketInfo busTicketDetails);

    void scanQRCode(MyTicketInfo busTicketDetails);
}
