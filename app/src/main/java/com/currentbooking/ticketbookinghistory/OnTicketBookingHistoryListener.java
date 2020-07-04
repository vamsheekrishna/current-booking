package com.currentbooking.ticketbookinghistory;

import com.currentbooking.ticketbookinghistory.models.TicketViewModel;

public interface OnTicketBookingHistoryListener {
    void showLiveTickets();
    void viewTicket(TicketViewModel liveTicketsModel);
}
