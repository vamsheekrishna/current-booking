package com.currentbooking.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.home.dummy.DummyContent;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;

public class ServicesHomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView mBalance;
    private String mParam1;
    private String mParam2;

    public ServicesHomeFragment() {
        // Required empty public constructor
    }

    public static ServicesHomeFragment newInstance(String param1, String param2) {
        ServicesHomeFragment fragment = new ServicesHomeFragment();
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
        requireActivity().setTitle("Services Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_services_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.list);

        String usrName = MyProfile.getInstance().getFirstName() +" "+ MyProfile.getInstance().getLastName();

        if (!usrName.isEmpty() || usrName != null)
        ((TextView)view.findViewById(R.id.name)).setText(usrName);
        else
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
        /*int mColumnCount = 3;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
        }*/

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        DummyContent.ITEMS.clear();
        DummyContent.createDummyItem(1, R.drawable.ic_launcher, "Current Booking");
        DummyContent.createDummyItem(2, R.drawable.tp, "Travel Pulse");
        DummyContent.createDummyItem(3, R.drawable.insurance, "Insurance");
        /*DummyContent.createDummyItem(3, R.drawable.insurance, "Insurance");
        DummyContent.createDummyItem(4, R.drawable.current_bus_booking, "Current Bus Booking");
        DummyContent.createDummyItem(5, R.drawable.air_hotel_booking, "Hotel Booking");
        DummyContent.createDummyItem(6, R.drawable.car_rental, "Car Rental");
        DummyContent.createDummyItem(7, R.drawable.cash_cards, "Cash Cards");
        DummyContent.createDummyItem(8, R.drawable.dth, "DTH");
        DummyContent.createDummyItem(10, R.drawable.e_paylater, "e-Pay Later");
        DummyContent.createDummyItem(11, R.drawable.electricity, "Electricity");
        DummyContent.createDummyItem(12, R.drawable.gold_loan, "Gold Loan");*/
        recyclerView.setAdapter(new MyServiceRecyclerViewAdapter(DummyContent.ITEMS, this));
    }

    @Override
    public void onClick(View view) {
            DummyContent.DummyItem mItem = (DummyContent.DummyItem) view.getTag();
            Intent intent;
            if(mItem.id == 1) {
                intent = new Intent(getActivity(), TicketBookingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            /*else if(mItem.id == 2) {
                intent = new Intent(getActivity(), DMTActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if(mItem.id == 10) {
                intent = new Intent(getActivity(), AEPSActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if(mItem.id == 3) {
                intent = new Intent(getActivity(), MFS100Test.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }*/
            else {
                showDialog("", getString(R.string.feature_availability_msg));
            }
    }
}
