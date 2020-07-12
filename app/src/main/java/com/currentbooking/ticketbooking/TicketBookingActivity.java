package com.currentbooking.ticketbooking;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
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
            addFragment(TicketBookingHomeFragment.newInstance("", ""), "TicketBookingHomeFragment", false);
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
    public void gotoConfirmTicket(String busType, GetFareResponse.FareDetails fareDetails, String passengerDetails) {
        fareDetails.setPassengerDetails(passengerDetails);
        addFragment(ConfirmTicketFragment.newInstance(busType, fareDetails), "ConfirmTicketFragment", true);
    }

    @Override
    public void gotoPassengerDetails(String busType) {
        addFragment(PassengerDetailsFragment.newInstance(busType), "PassengerDetailsFragment", true);
    }

    @Override
    public void gotoPayment(String passengerDetails, GetFareResponse.FareDetails fareDetails) {
        replaceFragment(PaymentFragment.newInstance(passengerDetails, fareDetails), "PaymentFragment", true);
    }

    @Override
    public void gotoTicketStatus(String passengerDetails, CCAvenueResponse ccAvenueResponse) {
        replaceFragment(TicketStatusFragment.newInstance(passengerDetails, ccAvenueResponse), "TicketStatusFragment", true);
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