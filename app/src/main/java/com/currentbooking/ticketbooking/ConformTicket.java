package com.currentbooking.ticketbooking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.currentbooking.R;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;

public class ConformTicket extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ConformTicket() {
        // Required empty public constructor
    }
    OnTicketBookingListener mListener;
    public static ConformTicket newInstance(String param1, String param2) {
        ConformTicket fragment = new ConformTicket();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Confirm Ticket");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_conform_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView)view.findViewById(R.id.first_name)).setText(MyProfile.getInstance().getFirstName());
        ((TextView)view.findViewById(R.id.last_name)).setText(MyProfile.getInstance().getLastName());
        ((TextView)view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView)view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        AppCompatTextView dob = view.findViewById(R.id.dob);
        dob.setText(MyProfile.getInstance().getDob());
        ((TextView)view.findViewById(R.id.address1)).setText(MyProfile.getInstance().getAddress1());
        ((TextView)view.findViewById(R.id.address2)).setText(MyProfile.getInstance().getAddress2());
        ((TextView)view.findViewById(R.id.state)).setText(MyProfile.getInstance().getState());
        ((TextView)view.findViewById(R.id.pin_code)).setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.edit_profile).setVisibility(View.GONE);
        view.findViewById(R.id.conform).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mListener.goToPayment();
    }
}