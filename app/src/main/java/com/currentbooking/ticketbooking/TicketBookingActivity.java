package com.currentbooking.ticketbooking;

import android.os.Bundle;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingActivity extends BaseNavigationDrawerActivity implements OnTicketBookingListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(TicketBookingHomeFragment.newInstance("",""),"TicketBookingHomeFragment", false);
    }

    @Override
    public void goToHome() {
        replaceFragment(TicketBookingHomeFragment.newInstance("", ""), "TicketBookingHomeFragment", true);
    }

    @Override
    public void goToSelectBus() {
        replaceFragment(SelectBusFragment.newInstance("", ""), "SelectBusFragment", true);
    }

    @Override
    public void goToConformTicket() {
        replaceFragment(ConformTicket.newInstance("", ""), "ConformTicket", true);
    }

    @Override
    public void goToPayment() {
        replaceFragment(PaymentFragment.newInstance("", ""), "PaymentFragment", true);
    }

    @Override
    public void goToTicketStatus() {
        replaceFragment(TicketStatus.newInstance("", ""), "TicketStatus", true);
    }

    @Override
    public void goToOptionSelection() {
        replaceFragment(OptionSelection.newInstance("", ""), "OptionSelection", true);
    }
}