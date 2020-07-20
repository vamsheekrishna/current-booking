package com.currentbooking.ticketbookinghistory;

import com.currentbooking.ticketbooking.BaseListener;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;

public interface OnTicketBookingHistoryListener extends BaseListener {
    void showLiveTickets();
    void viewTicket(MyTicketInfo liveTicketsModel);

    void generateQRCode(MyTicketInfo busTicketDetails);

    void scanQRCode(MyTicketInfo busTicketDetails);
}
