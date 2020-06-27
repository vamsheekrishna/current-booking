package com.currentbooking.ticketbookinghistory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingHistoryActivity extends BaseNavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(TodayTicketsFragment.newInstance("", ""), "TodayTicketsFragment", false);
    }
}