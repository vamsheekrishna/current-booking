package com.currentbooking.ticketbooking;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

// import com.currentbooking.interfaces.CallBackInterface;

public class TicketBookingActivity extends BaseNavigationDrawerActivity implements OnTicketBookingListener {

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
            TicketBookingViewModel ticketBookingModule = new ViewModelProvider(this).get(TicketBookingViewModel.class);
            ticketBookingModule.getConcession("MSRTC");
            addFragment(TicketBookingHomeFragment.newInstance("", ""), "TicketBookingHomeFragment", false);
            //BusObject busObject = new BusObject();
            //addFragment(ConfirmTicketFragment.newInstance(busObject, "MSRTC"), "ConfirmTicketFragment", false);
        }
    }


    @Override
    public void goToHome() {
        replaceFragment(TicketBookingHomeFragment.newInstance("", ""), "TicketBookingHomeFragment", true);
    }

    @Override
    public void goToSelectBus(String busOperatorName, String busType) {
        replaceFragment(SelectBusesFragment.newInstance(busOperatorName, busType), "SelectBusFragment", true);
    }

    @Override
    public void goToConfirmTicket(String busOperatorName, BusObject busObject) {
        replaceFragment(ConfirmTicketFragment.newInstance(busObject, busOperatorName), "ConfirmTicketFragment", true);
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