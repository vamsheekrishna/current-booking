package com.currentbooking.ticketbookinghistory;

import android.os.Bundle;

import com.currentbooking.ticketbookinghistory.models.AvailableTickets;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingHistoryActivity extends BaseNavigationDrawerActivity implements OnTicketBookingHistoryListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(LiveTicketFragment.newInstance("", ""), "LiveTicketFragment", false);
    }

    @Override
    public void showLiveTickets() {
        replaceFragment(LiveTicketFragment.newInstance("", ""), "ViewTicketFragment", true);
    }

    @Override
    public void viewTicket(AvailableTickets busTicketDetails) {
        replaceFragment(ViewTicketFragment.newInstance(busTicketDetails, ""), "LiveTicketFragment", true);
    }
}