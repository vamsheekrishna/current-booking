package com.currentbooking.ticketbooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.views.BaseFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TicketStatusFragment extends BaseFragment {

    private static final String ARG_BOOKING_STATUS = "BookingStatus";
    private static final String ARG_BOOKING_DETAILS = "BookingDetails";
    private static final String CC_AVENUE_RESPONSE = "ccavenue";
    private static final String ARG_PASSENGER_DETAILS = "PassengerDetails";

    // TODO: Rename and change types of parameters
    // private boolean bookingStatus;
    // private Object bookingDetails;
    private TicketBookingViewModel ticketBookingModule;
    private CCAvenueResponse ccAvenueResponse;
    //private String passengerDetails;

    private TicketStatusFragment() {
        // Required empty public constructor
    }

    /*public static TicketStatusFragment newInstance(boolean bookingStatus, String passengerDetails, Object bookingDetails) {
        TicketStatusFragment fragment = new TicketStatusFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_BOOKING_STATUS, bookingStatus);
        args.putSerializable(ARG_BOOKING_DETAILS, (Serializable) bookingDetails);
        args.putString(ARG_PASSENGER_DETAILS, passengerDetails);
        fragment.setArguments(args);
        return fragment;
    }*/
    public static TicketStatusFragment newInstance(CCAvenueResponse ccAvenueResponse) {
        TicketStatusFragment fragment = new TicketStatusFragment();
        Bundle args = new Bundle();
        // args.putBoolean(ARG_BOOKING_STATUS, bookingStatus);
        args.putSerializable(CC_AVENUE_RESPONSE, ccAvenueResponse);
        // args.putString(ARG_PASSENGER_DETAILS, passengerDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            // bookingStatus = extras.getBoolean(ARG_BOOKING_STATUS);
            ccAvenueResponse = (CCAvenueResponse) extras.getSerializable(CC_AVENUE_RESPONSE);
            // passengerDetails = extras.getString(ARG_PASSENGER_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
        return inflater.inflate(R.layout.fragment_ticket_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BusObject busDetails = ticketBookingModule.getSelectedBusObject().getValue();
        ImageView ivBookingStatusField = view.findViewById(R.id.iv_booking_status_image);
        LinearLayout paymentSuccessBtnLayoutField = view.findViewById(R.id.payment_success_buttons_layout_field);
        TextView tvBookingStatusField = view.findViewById(R.id.tv_booking_status_field);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.booking_status));
        LinearLayout bookingSuccessLayoutField = view.findViewById(R.id.booking_success_layout_field);
        LinearLayout bookingFailedLayoutField = view.findViewById(R.id.booking_failed_layout_field);

        String busOperatorName = Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName();
        String busType = Objects.requireNonNull(ticketBookingModule.getSelectedBusType().getValue()).getBusTypeName();

        view.findViewById(R.id.btn_failed_try_again_field).setOnClickListener(v -> paymentFailedTryAgainBtnSelected());
        view.findViewById(R.id.btn_failed_go_to_home_field).setOnClickListener(v -> paymentFailedHomeBtnSelected());
        view.findViewById(R.id.btn_success_book_another_field).setOnClickListener(v -> paymentSuccessBookAnotherBtnSelected());
        view.findViewById(R.id.btn_success_go_to_home_field).setOnClickListener(v -> paymentSuccessHomeBtnSelected());

        ((TextView) view.findViewById(R.id.tv_ticket_number_field)).setText(ccAvenueResponse.getTicket_number());
        ((TextView) view.findViewById(R.id.tv_total_persons_bus_fare_price_field)).setText(ccAvenueResponse.getFare());
        ((TextView) view.findViewById(R.id.tv_total_persons_service_charge_or_gst_field)).setText(ccAvenueResponse.getService_charge());
        ((TextView) view.findViewById(R.id.tv_total_persons_total_fare_field)).setText(ccAvenueResponse.getTotal_fare());

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        ((TextView) view.findViewById(R.id.date_field)).setText(formattedDate);

        df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(c);
        ((TextView) view.findViewById(R.id.time_field)).setText(formattedDate);

        if (ccAvenueResponse.getStatus().equalsIgnoreCase("success")) {
            ivBookingStatusField.setImageResource(R.drawable.booking_success_icon);
            paymentSuccessBtnLayoutField.setVisibility(View.VISIBLE);
            tvBookingStatusField.setText(getString(R.string.booking_successful));
            bookingSuccessLayoutField.setVisibility(View.VISIBLE);
            bookingFailedLayoutField.setVisibility(View.GONE);
        } else {
            ivBookingStatusField.setImageResource(R.drawable.close_icon);
            paymentSuccessBtnLayoutField.setVisibility(View.GONE);
            tvBookingStatusField.setText(getString(R.string.payment_failed));
            bookingSuccessLayoutField.setVisibility(View.GONE);
            bookingFailedLayoutField.setVisibility(View.VISIBLE);
        }

        if(busDetails != null) {
            String busRouteName = String.format("%s %s", busOperatorName.toUpperCase(), busDetails.getBusServiceNo());
            ((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);
            ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busType);

            String busRoute = String.format("%s to %s", busDetails.getOriginStopName(), busDetails.getLastStopName());
            Calendar journeyStartCalendar = DateUtilities.getCalendarFromDate(busDetails.getOriginDateTime());
            Calendar journeyEndCalendar = DateUtilities.getCalendarFromDate(busDetails.getLastStopDateTime());
            String startTime = DateUtilities.getTimeFromCalendar(journeyStartCalendar);
            String endTime = DateUtilities.getTimeFromCalendar(journeyEndCalendar);
            String hoursDifference = String.format("%s Hrs", DateUtilities.getJourneyHours(journeyStartCalendar, journeyEndCalendar));

            ((TextView) view.findViewById(R.id.tv_bus_route_field)).setText(busRoute);
            ((TextView) view.findViewById(R.id.tv_bus_journey_start_time_field)).setText(startTime);
            ((TextView) view.findViewById(R.id.tv_bus_journey_hours_field)).setText(hoursDifference);
            ((TextView) view.findViewById(R.id.tv_bus_journey_end_time_field)).setText(endTime);
        }
        // ((TextView) view.findViewById(R.id.tv_bus_fare_price_field)).setText(ccAvenueResponse.getTotal_fare());
    }

    private void paymentSuccessHomeBtnSelected() {

    }

    private void paymentSuccessBookAnotherBtnSelected() {

    }

    private void paymentFailedHomeBtnSelected() {

    }

    private void paymentFailedTryAgainBtnSelected() {

    }
}