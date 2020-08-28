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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.adapters.LiveTicketsAdapter;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.Constants;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.RecyclerTouchListener;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.utilits.DateUtilities.CALENDAR_DATE_FORMAT_THREE;
import static com.currentbooking.utilits.views.BaseNavigationDrawerActivity.SHOW_ALL_RECORDS;

/**
 * Created by Satya Seshu on 03/07/20.
 */
public class LiveTicketFragment extends BaseFragment implements View.OnClickListener {

    private LiveTicketsAdapter liveTicketsAdapter;
    private RecyclerView liveTicketsListRecyclerField;
    private SwipeRefreshLayout swipeRefreshLayout;

    public LiveTicketFragment() {
        // Required empty public constructor
    }

    OnTicketBookingHistoryListener mListener;

    public static LiveTicketFragment newInstance(boolean b) {

        LiveTicketFragment fragment = new LiveTicketFragment();
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
    }

    private void loadUIComponents(View view) {

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_field);
        swipeRefreshLayout.setProgressViewOffset(false, 300, 300);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this::updateTickets);
        liveTicketsListRecyclerField = view.findViewById(R.id.live_ticket_bookings_list_field);
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
            Collections.sort(liveTicketsList, (t1, t2) -> {
                Integer t1OrderID = Utils.getIntegerValueFromString(t1.getOrder_id());
                Integer t2OrderID = Utils.getIntegerValueFromString(t2.getOrder_id());
                return t2OrderID - t1OrderID;
            });
            liveTicketsAdapter = new LiveTicketsAdapter(requireActivity(), liveTicketsList);
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

    private void updateTickets() {
        swipeRefreshLayout.setRefreshing(true);
        if(NetworkUtility.isNetworkConnected(requireActivity())) {
            String date = DateUtilities.getTodayDateString(CALENDAR_DATE_FORMAT_THREE);
            String id = MyProfile.getInstance().getUserId();
            TicketBookingServices service = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
            progressDialog.show();
            service.getCurrentBookingTicket(date, id, "").enqueue(new Callback<TodayTickets>() {
                @Override
                public void onResponse(@NotNull Call<TodayTickets> call, @NotNull Response<TodayTickets> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    TodayTickets todayTickets = response.body();
                    if (todayTickets != null) {
                        if (todayTickets.getStatus().equalsIgnoreCase("success")) {
                            ArrayList<MyTicketInfo> data = todayTickets.getAvailableTickets();
                            if (null != data && data.size() > 0) {
                                Iterator<MyTicketInfo> iterator = data.iterator();
                                while (iterator.hasNext()) {
                                    MyTicketInfo myTicketInfo = iterator.next();
                                    if (myTicketInfo.getTicket_status().equalsIgnoreCase(Constants.KEY_EXPIRED) ||
                                            myTicketInfo.getTicket_status().equalsIgnoreCase(Constants.KEY_FAILED)) {
                                        iterator.remove();
                                    }
                                }

                                MyProfile.getInstance().setTodayTickets(data);
                                MyProfile.getInstance().getCurrentBookingTicketCount().setValue(data.size());

                                if (liveTicketsAdapter != null) {
                                    liveTicketsAdapter.updateTickets(data);
                                    liveTicketsAdapter.notifyDataSetChanged();
                                } else {
                                    liveTicketsAdapter = new LiveTicketsAdapter(requireActivity(), data);
                                    liveTicketsListRecyclerField.setAdapter(liveTicketsAdapter);
                                }
                            }
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<TodayTickets> call, @NotNull Throwable t) {
                    // showDialog("onFailure", "" + t.getMessage());
                    LoggerInfo.errorLog("getAvailableLiveTickets OnFailure", t.getMessage());
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            showDialog(getString(R.string.internet_fail));
        }
    }

    private void updateAdapter() {

    }

    @Override
    public void onClick(View v) {

    }
}