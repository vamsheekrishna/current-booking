package com.currentbooking.ticketbooking;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class TicketBookingActivity extends BaseNavigationDrawerActivity implements OnTicketBookingListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            addFragment(TicketBookingHomeFragment.newInstance(), "TicketBookingHomeFragment", false);
        }
    }

    @Override
    public void goToHome() {
        replaceFragment(TicketBookingHomeFragment.newInstance(), "TicketBookingHomeFragment", true);
        showBadge(true);
        showHamburgerIcon(true);
    }

    @Override
    public void gotoSelectBus(String busOperatorName, String busType) {
        addFragment(SelectBusesFragment.newInstance(busOperatorName, busType), "SelectBusFragment", true);

        showBadge(false);
        showHamburgerIcon(false);
    }

    @Override
    public void gotoConfirmTicket(String busType, GetFareResponse.FareDetails fareDetails, String passengerDetails) {
        fareDetails.setPassengerDetails(passengerDetails);
        addFragment(ConfirmTicketFragment.newInstance(busType, fareDetails), "ConfirmTicketFragment", true);
        showBadge(false);
        showHamburgerIcon(false);
    }

    @Override
    public void gotoPassengerDetails(String busType) {
        addFragment(PassengerDetailsFragment.newInstance(busType), "PassengerDetailsFragment", true);
        showBadge(false);
        showHamburgerIcon(false);
    }

    @Override
    public void gotoPayment(String passengerDetails, GetFareResponse.FareDetails fareDetails) {
        addFragment(PaymentFragment.newInstance(passengerDetails, fareDetails), "PaymentFragment", true);
        showBadge(false);
        showHamburgerIcon(false);
    }

    @Override
    public void gotoTicketStatus(String passengerDetails, CCAvenueResponse ccAvenueResponse) {
        addFragment(TicketStatusFragment.newInstance(passengerDetails, ccAvenueResponse), "TicketStatusFragment", false);
        showBadge(false);
        showHamburgerIcon(false);
    }

    public void gotoOptionSelection(int index, String title) {
        replaceFragment(OptionSelectionFragment.newInstance(index, title), "OptionSelection", true);
        showBadge(false);
        showHamburgerIcon(false);
    }

    @Override
    public void gotoBusStopSelect(int index, double latitude, double longitude) {
        addFragment(BusPointFragment.newInstance(index, latitude, longitude), "BusPointFragment", true);
        showBadge(false);
        showHamburgerIcon(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        BaseFragment f = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.base_container);
        if (! (f instanceof TicketStatusFragment)) {
            super.onBackPressed();
        }
    }
}