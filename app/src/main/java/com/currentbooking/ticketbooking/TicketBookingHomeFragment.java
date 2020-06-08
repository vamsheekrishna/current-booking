package com.currentbooking.ticketbooking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.currentbooking.R;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;

public class TicketBookingHomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TicketBookingViewModel ticketBookingViewModel;

    private String mParam1;
    private String mParam2;

    public TicketBookingHomeFragment() {
        // Required empty public constructor
    }

    public static TicketBookingHomeFragment newInstance(String param1, String param2) {
        TicketBookingHomeFragment fragment = new TicketBookingHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        requireActivity().setTitle(getString(R.string.ticket_booking));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_ticket_booking_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_select_type);
        view.findViewById(R.id.swipe_points).setOnClickListener(this);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<String> busTypes = new ArrayList<>();
        busTypes.add("Ac");
        busTypes.add("Non Ac");
        busTypes.add("Ac Sleeper");
        busTypes.add("Non Ac Sleeper");
        busTypes.add("Luxury");
        busTypes.add("Deluxe");
        recyclerView.setAdapter(new BusTypeAdapter(busTypes));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipe_points:
                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1000);
                rotate.setInterpolator(new LinearInterpolator());
                //ImageView image= (ImageView) findViewById(R.id.imageView);
                rotate.setFillAfter(true);
                v.startAnimation(rotate);
                break;
        }
    }
}