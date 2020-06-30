package com.currentbooking.ticketbooking;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingActivity extends BaseNavigationDrawerActivity implements OnTicketBookingListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
        } else {
            newString= extras.getString("STRING_I_NEED");
        }*/

        if (savedInstanceState == null) {
            TicketBookingViewModel ticketBookingModule = new ViewModelProvider(this).get(TicketBookingViewModel.class);
            // ticketBookingModule.getConcession("MSRTC");
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
    public void gotoSelectBus(String busOperatorName, String busType) {
        replaceFragment(SelectBusesFragment.newInstance(busOperatorName, busType), "SelectBusFragment", true);
    }

    @Override
    public void gotoConfirmTicket(String busType, GetFareResponse.FareDetails personsAddedList) {
        addFragment(ConfirmTicketFragment.newInstance(busType, personsAddedList), "ConfirmTicketFragment", true);
    }

    @Override
    public void gotoPassengerDetails(String busType) {
        replaceFragment(PassengerDetailsFragment.newInstance(busType), "PassengerDetailsFragment", true);
    }

    @Override
    public void gotoPayment(GetFareResponse.FareDetails fareDetails) {
        replaceFragment(PaymentFragment.newInstance("", fareDetails), "PaymentFragment", true);
    }

    @Override
    public void gotoTicketStatus(boolean ticketStatus, String passengerDetails, Object bookingDetails) {
        replaceFragment(TicketStatusFragment.newInstance(ticketStatus, passengerDetails, bookingDetails), "TicketStatusFragment", true);
    }

    public void gotoOptionSelection(int index) {
        replaceFragment(OptionSelectionFragment.newInstance(index, ""), "OptionSelection", true);
    }

    @Override
    public void gotoBusStopSelect(int index) {
        replaceFragment(BusPointFragment.newInstance(index, ""), "BusPointFragment", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}