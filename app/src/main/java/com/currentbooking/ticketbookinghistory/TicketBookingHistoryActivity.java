package com.currentbooking.ticketbookinghistory;

import android.os.Bundle;

import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingHistoryActivity extends BaseNavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(ViewTicketFragment.newInstance("", ""), "ViewTicketFragment", false);
    }
}