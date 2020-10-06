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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.adapters.LiveTicketsAdapter;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.Constants;
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.RecyclerTouchListener;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.utilits.views.BaseNavigationDrawerActivity.SHOW_ALL_RECORDS;

/**
 * Created by Satya Seshu on 27/07/20.
 */
public class BookingHistoryTicketFragment extends BaseFragment {

    private List<MyTicketInfo> ticketsHistoryList = new ArrayList<>();
    private RecyclerView liveTicketsListRecyclerField;
    private SwipeRefreshLayout swipeRefreshLayout;

    public BookingHistoryTicketFragment() {
        // Required empty public constructor
    }

    private OnTicketBookingHistoryListener mListener;

    public static BookingHistoryTicketFragment newInstance(boolean b) {
        BookingHistoryTicketFragment fragment = new BookingHistoryTicketFragment();
        Bundle args = new Bundle();
        args.putBoolean(SHOW_ALL_RECORDS, b);
        fragment.setArguments(args);
        return fragment;
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

        getHistoryTicketsList();
    }

    private void loadUIComponents(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_field);
        swipeRefreshLayout.setProgressViewOffset(false, 300, 300);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this::getHistoryTicketsList);
        liveTicketsListRecyclerField = view.findViewById(R.id.live_ticket_bookings_list_field);
        liveTicketsListRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider_two)));
        liveTicketsListRecyclerField.addItemDecoration(divider);

        view.findViewById(R.id.live_ticket_header_field).setVisibility(View.GONE);

        liveTicketsListRecyclerField.addOnItemTouchListener(new RecyclerTouchListener(requireActivity(), liveTicketsListRecyclerField, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                mListener.viewTicket(ticketsHistoryList.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getHistoryTicketsList() {
        swipeRefreshLayout.setRefreshing(true);
        ticketsHistoryList.clear();
        if(NetworkUtility.isNetworkConnected(requireActivity())) {
            String id = MyProfile.getInstance().getUserId();
            TicketBookingServices service = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
            progressDialog.show();
            service.getCurrentBookingTicket("", id, "").enqueue(new Callback<TodayTickets>() {
                @Override
                public void onResponse(@NotNull Call<TodayTickets> call, @NotNull Response<TodayTickets> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    TodayTickets todayTickets = response.body();
                    if (todayTickets != null) {
                        if (todayTickets.getStatus().equalsIgnoreCase("success")) {
                            ArrayList<MyTicketInfo> data = todayTickets.getAvailableTickets();
                            if (null != data && data.size() > 0) {
                                for (MyTicketInfo myTicketInfo : data) {
                                    if (myTicketInfo.getTicket_status().equalsIgnoreCase(Constants.KEY_EXPIRED)|| myTicketInfo.getTicket_status().equalsIgnoreCase(Constants.KEY_APPROVED)) {
                                        ticketsHistoryList.add(myTicketInfo);
                                    }
                                }
                            }
                        }
                    }
                    progressDialog.dismiss();
                    setBookingHistoryAdapter();
                }

                @Override
                public void onFailure(@NotNull Call<TodayTickets> call, @NotNull Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    // showDialog("onFailure", "" + t.getMessage());
                    LoggerInfo.errorLog("getAvailableLiveTickets OnFailure", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.internet_fail));
        }
    }

    private void setBookingHistoryAdapter() {
        if (!ticketsHistoryList.isEmpty()) {
            LiveTicketsAdapter liveTicketsAdapter = new LiveTicketsAdapter(requireActivity(), ticketsHistoryList);
            liveTicketsListRecyclerField.setAdapter(liveTicketsAdapter);
        }
    }
}
