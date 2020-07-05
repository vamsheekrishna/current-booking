package com.currentbooking.ticketbookinghistory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.adapters.LiveTicketsAdapter;
import com.currentbooking.ticketbookinghistory.models.TicketViewModel;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Satya Seshu on 03/07/20.
 */
public class LiveTicketFragment  extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView liveTicketsListRecyclerField;
    private List<TicketViewModel> liveTicketsList;

    public LiveTicketFragment() {
        // Required empty public constructor
    }

    OnTicketBookingHistoryListener mListener;
    public static LiveTicketFragment newInstance(String param1, String param2) {
        LiveTicketFragment fragment = new LiveTicketFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        liveTicketsListRecyclerField = view.findViewById(R.id.live_ticket_bookings_list_fieid);
        liveTicketsListRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider_two)));
        liveTicketsListRecyclerField.addItemDecoration(divider);

        liveTicketsList = new ArrayList<>();

        TicketViewModel liveTicketsModel = new TicketViewModel();
        liveTicketsModel.setStatus(0);
        liveTicketsList.add(liveTicketsModel);

        liveTicketsModel = new TicketViewModel();
        liveTicketsModel.setStatus(1);
        liveTicketsList.add(liveTicketsModel);

        liveTicketsModel = new TicketViewModel();
        liveTicketsModel.setStatus(2);
        liveTicketsList.add(liveTicketsModel);

        liveTicketsModel = new TicketViewModel();
        liveTicketsModel.setStatus(3);
        liveTicketsList.add(liveTicketsModel);

        LiveTicketsAdapter liveTicketsAdapter = new LiveTicketsAdapter(requireActivity(), liveTicketsList, this);
        liveTicketsListRecyclerField.setAdapter(liveTicketsAdapter);
    }

    @Override
    public void onClick(View v) {
        mListener.viewTicket(new TicketViewModel());
    }
}