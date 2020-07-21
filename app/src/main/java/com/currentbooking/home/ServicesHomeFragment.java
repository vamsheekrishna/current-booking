package com.currentbooking.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.BaseListener;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ServicesHomeFragment extends BaseFragment {

    private BaseListener mListener;

    public ServicesHomeFragment() {
        // Required empty public constructor
    }

    public static ServicesHomeFragment newInstance() {
        return new ServicesHomeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.services_home));
        mListener.showBadge(true);
        mListener.showHamburgerIcon(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (BaseListener)context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyProfile.getInstance().updateLiveTickets(progressDialog);
        MyProfile.getInstance().getCurrentBookingTicketCount().observe(Objects.requireNonNull(getActivity()), integer -> {
            mListener.updateBadgeCount();
        });
        return inflater.inflate(R.layout.fragment_services_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            String userName = String.format("%s %s", myProfile.getFirstName(), myProfile.getLastName());
            if (!TextUtils.isEmpty(userName))
                ((TextView) view.findViewById(R.id.user_name_field)).setText(userName);
        }

        view.findViewById(R.id.current_booking_layout_field).setOnClickListener(v -> currentBookingSelected());
        view.findViewById(R.id.advance_booking_layout_field).setOnClickListener(v -> advanceBookingSelected());
        view.findViewById(R.id.travel_pulse_layout_field).setOnClickListener(v -> travelPulseSelected());
        view.findViewById(R.id.shopping_cart_layout_field).setOnClickListener(v -> shoppingCartSelected());
    }

    private void currentBookingSelected() {
        Intent intent = new Intent(getActivity(), TicketBookingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void advanceBookingSelected() {
        showDialog("", getString(R.string.feature_availability_msg));
    }

    private void travelPulseSelected() {
        showDialog("", getString(R.string.feature_availability_msg));
    }

    private void shoppingCartSelected() {
        showDialog("", getString(R.string.feature_availability_msg));
    }
}
