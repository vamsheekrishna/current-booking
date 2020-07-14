package com.currentbooking.ticketbookinghistory;

import com.currentbooking.ticketbookinghistory.models.AvailableTickets;

public interface OnTicketBookingHistoryListener {
    void showLiveTickets();
    void viewTicket(AvailableTickets liveTicketsModel);
}
