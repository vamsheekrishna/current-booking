package com.currentbooking.ticketbookinghistory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.adapters.LiveTicketsAdapter;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.RecyclerTouchListener;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Satya Seshu on 03/07/20.
 */
public class LiveTicketFragment  extends BaseFragment implements View.OnClickListener {

    public LiveTicketFragment() {
        // Required empty public constructor
    }

    OnTicketBookingHistoryListener mListener;
    public static LiveTicketFragment newInstance() {
        return new LiveTicketFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingHistoryListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_ticket, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("My Tickets");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView liveTicketsListRecyclerField = view.findViewById(R.id.live_ticket_bookings_list_fieid);
        liveTicketsListRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider_two)));
        liveTicketsListRecyclerField.addItemDecoration(divider);

        List<MyTicketInfo> liveTicketsList = new ArrayList<>();
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            ArrayList<MyTicketInfo> liveTickets = myProfile.getTodayTickets().getValue();
            if (liveTickets != null && !liveTickets.isEmpty()) {
                liveTicketsList.addAll(liveTickets);
            }
        }

        TextView liveTicketHeaderField = view.findViewById(R.id.live_ticket_header_field);
        String headerText = String.format(Locale.getDefault(), "%d Live Tickets Found on %s", liveTicketsList.size(), DateUtilities.getCurrentDate());
        liveTicketHeaderField.setText(headerText);

        if(!liveTicketsList.isEmpty()) {
            LiveTicketsAdapter liveTicketsAdapter = new LiveTicketsAdapter(requireActivity(), liveTicketsList);
            liveTicketsListRecyclerField.setAdapter(liveTicketsAdapter);
        }

        liveTicketsListRecyclerField.addOnItemTouchListener(new RecyclerTouchListener(requireActivity(), liveTicketsListRecyclerField, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                mListener.viewTicket(liveTicketsList.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {

    }
}