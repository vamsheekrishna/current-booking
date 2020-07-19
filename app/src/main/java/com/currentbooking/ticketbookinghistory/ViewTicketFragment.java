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
import com.currentbooking.ticketbookinghistory.adapters.PassengerDetailsAdapter;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.ticketbookinghistory.models.PassengerDetailsModel;
import com.currentbooking.utilits.Constants;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.List;
import java.util.Objects;

public class ViewTicketFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyTicketInfo busTicketDetails;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnTicketBookingHistoryListener mListener;

    public ViewTicketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingHistoryListener)context;
    }

    public static ViewTicketFragment newInstance(MyTicketInfo ticketDetails, String param2) {
        ViewTicketFragment fragment = new ViewTicketFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, ticketDetails);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            busTicketDetails = (MyTicketInfo) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.view_qr_code).setOnClickListener(this);
        view.findViewById(R.id.scan_qr_code).setOnClickListener(this);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView passengerListRecyclerField = view.findViewById(R.id.passenger_list_recycler_field);
        passengerListRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider)));
        passengerListRecyclerField.addItemDecoration(divider);
        TextView tvBusRouteNameField = view.findViewById(R.id.tv_route_name_field);
        TextView tvBusRouteField = view.findViewById(R.id.tv_bus_route_field);
        TextView tvJourneyStartTimeField = view.findViewById(R.id.tv_bus_journey_start_time_field);
        TextView tvJourneyEndTimeField = view.findViewById(R.id.tv_bus_journey_end_time_field);
        TextView tvJourneyHrsField = view.findViewById(R.id.tv_bus_journey_hours_field);

        String busRouteName = String.format("%s %s", busTicketDetails.getOperator_name().toUpperCase(), busTicketDetails.getBus_service_no());
        tvBusRouteNameField.setText(busRouteName);

        String busRoute = String.format("%s to %s", busTicketDetails.getFrom_stop(), busTicketDetails.getTo_stop());
        tvBusRouteField.setText(busRoute);
        tvJourneyStartTimeField.setText(busTicketDetails.getStart_time());
        tvJourneyEndTimeField.setText(busTicketDetails.getDrop_time());
        String journeyHrs = String.format("%s Hrs", busTicketDetails.getHours());
        tvJourneyHrsField.setText(journeyHrs);

        List<PassengerDetailsModel> passengerDetailsList = busTicketDetails.getPassengerDetailsList();

        PassengerDetailsAdapter passengerDetailsAdapter = new PassengerDetailsAdapter(requireActivity(), passengerDetailsList);
        passengerListRecyclerField.setAdapter(passengerDetailsAdapter);

        TextView tvTotalPersonsFareField = view.findViewById(R.id.tv_total_persons_bus_fare_price_field);
        tvTotalPersonsFareField.setText(busTicketDetails.getTotal());

        TextView tvServiceChargeOrGstField = view.findViewById(R.id.tv_total_persons_service_charge_or_gst_field);
        TextView tvTotalUpdatedFareField = view.findViewById(R.id.tv_total_persons_total_fare_field);

        TextView tvBookingStatusField = view.findViewById(R.id.tv_booking_status_field);
        String ticketStatus = busTicketDetails.getTicket_status();
        if(ticketStatus.equalsIgnoreCase(Constants.KEY_APPROVED)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green));
        } else if(ticketStatus.equalsIgnoreCase(Constants.KEY_REJECTED)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red_two));
        } else if(ticketStatus.equalsIgnoreCase(Constants.KEY_PENDING)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.orange_color));
        } else {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.pink_color));
        }
        tvBookingStatusField.setText(ticketStatus);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.scan_qr_code) {
            mListener.scanQRCode(busTicketDetails);
        } else {
            mListener.generateQRCode(busTicketDetails);
        }
    }
}