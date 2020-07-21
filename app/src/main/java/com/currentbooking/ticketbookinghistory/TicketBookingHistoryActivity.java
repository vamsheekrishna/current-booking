package com.currentbooking.ticketbookinghistory;

import android.os.Bundle;
import android.view.Menu;

import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingHistoryActivity extends BaseNavigationDrawerActivity implements OnTicketBookingHistoryListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBadge(false);
        showHamburgerIcon(false);
        addFragment(LiveTicketFragment.newInstance("", ""), "LiveTicketFragment", false);
    }

    @Override
    public void showLiveTickets() {
        replaceFragment(LiveTicketFragment.newInstance("", ""), "ViewTicketFragment", true);
    }

    @Override
    public void viewTicket(MyTicketInfo busTicketDetails) {
        replaceFragment(ViewTicketFragment.newInstance(busTicketDetails, ""), "LiveTicketFragment", true);
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