package com.currentbooking.ticketbookinghistory;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

import org.jetbrains.annotations.NotNull;

public class TicketBookingHistoryActivity extends BaseNavigationDrawerActivity implements OnTicketBookingHistoryListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBadge(false);
        showHamburgerIcon(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean isShowAllRecords = extras.getBoolean(SHOW_ALL_RECORDS);
            if(isShowAllRecords) {
                addFragment(BookingHistoryTicketFragment.newInstance(true), "BookingHistoryTicketFragment", false);
            } else {
                addFragment(LiveTicketFragment.newInstance(true), "LiveTicketFragment", false);
            }
        } else {
            addFragment(LiveTicketFragment.newInstance(true), "LiveTicketFragment", false);
        }
    }

    @Override
    public void showLiveTickets() {
        replaceFragment(LiveTicketFragment.newInstance(false), "LiveTicketFragment", true);
    }

    @Override
    public void viewTicket(MyTicketInfo busTicketDetails) {
        replaceFragment(ViewTicketFragment.newInstance(busTicketDetails), "ViewTicketFragment", true);
    }

    @Override
    public void generateQRCode(MyTicketInfo busTicketDetails) {
        replaceFragment(GenerateQRCode.newInstance(busTicketDetails, ""), "LiveTicketFragment", true);
    }

    @Override
    public void scanQRCode(MyTicketInfo busTicketDetails) {
       replaceFragment(QRScannerFragment.newInstance(busTicketDetails, ""), "QRScannerFragment", true);
    }
}