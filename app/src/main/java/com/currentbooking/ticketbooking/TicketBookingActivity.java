package com.currentbooking.ticketbooking;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

// import com.currentbooking.interfaces.CallBackInterface;

public class TicketBookingActivity extends BaseNavigationDrawerActivity implements OnTicketBookingListener/*, CallBackInterface*/ {

    private TicketBookingViewModel ticketBookingModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
        } else {
            newString= extras.getString("STRING_I_NEED");
        }*/
        if(savedInstanceState == null) {
            ticketBookingModule = new ViewModelProvider(this).get(TicketBookingViewModel.class);
            addFragment(TicketStatusFragment.newInstance("", ""), "TicketBookingHomeFragment", false);
        }
    }


    @Override
    public void goToHome() {
        replaceFragment(TicketBookingHomeFragment.newInstance("", ""), "TicketBookingHomeFragment", true);
    }

    @Override
    public void goToSelectBus() {
        replaceFragment(SelectBusesFragment.newInstance("", ""), "SelectBusFragment", true);
    }

    @Override
    public void goToConformTicket(BusObject busObject) {
        replaceFragment(ConformTicket.newInstance("", ""), "ConformTicket", true);
    }

    @Override
    public void goToPayment() {
        replaceFragment(PaymentFragment.newInstance("", ""), "PaymentFragment", true);
    }

    @Override
    public void goToTicketStatus() {
        replaceFragment(TicketStatusFragment.newInstance("", ""), "TicketStatusFragment", true);
    }

    public void goToOptionSelection(int index) {
        replaceFragment(OptionSelectionFragment.newInstance(index, ""), "OptionSelection", true);
    }

    @Override
    public void goToBusStopSelect(int index) {
        replaceFragment(BusPointFragment.newInstance(index, ""), "BusPointFragment", true);
    }
}