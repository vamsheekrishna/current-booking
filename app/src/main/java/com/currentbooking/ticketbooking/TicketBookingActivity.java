package com.currentbooking.ticketbooking;

import android.os.Bundle;
// import com.currentbooking.interfaces.CallBackInterface;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingActivity extends BaseNavigationDrawerActivity implements OnTicketBookingListener/*, CallBackInterface*/ {

    private String optionSelectedState;

    private TicketBookingViewModel ticketBookingModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticketBookingModule = new ViewModelProvider(this).get(TicketBookingViewModel.class);
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

    /*@Override
    public void goToOptionSelection() {
        replaceFragment(OptionSelectionFragment.newInstance("", ""), "OptionSelection", true);
    }*/

    /*@Override
    public void callBackReceived(Object pObject) {
        if (pObject instanceof String) {
            optionSelectedState = (String) pObject;
        }*/
    public void goToOptionSelection(int index) {
        replaceFragment(OptionSelection.newInstance(index, ""), "OptionSelection", true);
    }
}