package com.currentbooking.ticketbookinghistory;

import android.os.Bundle;

import com.currentbooking.ticketbookinghistory.models.TicketViewModel;
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
    public void viewTicket(TicketViewModel liveTicketsModel) {
        replaceFragment(ViewTicketFragment.newInstance("", ""), "LiveTicketFragment", true);
    }
}