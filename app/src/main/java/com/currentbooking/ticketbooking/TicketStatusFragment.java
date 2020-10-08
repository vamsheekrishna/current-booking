package com.currentbooking.ticketbooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.MyViewModelFactory;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.utilits.DateUtilities.CALENDAR_DATE_FORMAT_THREE;

public class TicketStatusFragment extends BaseFragment implements MvvmView.View {

    private static final String ARG_BOOKING_STATUS = "BookingStatus";
    private static final String ARG_BOOKING_DETAILS = "BookingDetails";
    private static final String CC_AVENUE_RESPONSE = "ccavenue";
    private static final String ARG_PASSENGER_DETAILS = "PassengerDetails";

    // TODO: Rename and change types of parameters
    private boolean bookingStatus;
    private TicketBookingViewModel ticketBookingModule;
    private CCAvenueResponse ccAvenueResponse;
    private String passengerDetails;
    private OnTicketBookingListener mListener;

    private TicketStatusFragment() {
        // Required empty public constructor
    }

    public static TicketStatusFragment newInstance(String passengerDetails, CCAvenueResponse ccAvenueResponse) {
        TicketStatusFragment fragment = new TicketStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PASSENGER_DETAILS, passengerDetails);
        args.putSerializable(CC_AVENUE_RESPONSE, ccAvenueResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnTicketBookingListener) {

            mListener = (OnTicketBookingListener) context;
        }
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            passengerDetails = getArguments().getString(ARG_PASSENGER_DETAILS);
            ccAvenueResponse = (CCAvenueResponse) extras.getSerializable(CC_AVENUE_RESPONSE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Ticket Status");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        //ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
        MyProfile.getInstance().getCurrentBookingTicketCount().observe(Objects.requireNonNull(getActivity()), integer -> {
            mListener.updateBadgeCount();
            mListener.showBadge(true);
        });
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity()), new MyViewModelFactory(this)).get(TicketBookingViewModel.class);
        return inflater.inflate(R.layout.fragment_ticket_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK ) {
               return true;
            }
            return false;
        });
        BusObject busDetails = ticketBookingModule.getSelectedBusObject().getValue();
        ImageView ivBookingStatusField = view.findViewById(R.id.iv_booking_status_image);
        LinearLayout paymentSuccessBtnLayoutField = view.findViewById(R.id.payment_success_buttons_layout_field);
        TextView tvBookingStatusField = view.findViewById(R.id.tv_booking_status_field);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.booking_status));
        LinearLayout bookingSuccessLayoutField = view.findViewById(R.id.booking_success_layout_field);
        LinearLayout bookingFailedLayoutField = view.findViewById(R.id.booking_failed_layout_field);

        String busOperatorName = Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName();
        //String busType = Objects.requireNonNull(ticketBookingModule.getSelectedBusType().getValue()).getBusTypeName();

        view.findViewById(R.id.btn_failed_try_again_field).setOnClickListener(v -> paymentFailedTryAgainBtnSelected());
        view.findViewById(R.id.btn_failed_go_to_home_field).setOnClickListener(v -> paymentFailedHomeBtnSelected());
        view.findViewById(R.id.btn_success_book_another_field).setOnClickListener(v -> paymentSuccessBookAnotherBtnSelected());
        view.findViewById(R.id.btn_success_go_to_home_field).setOnClickListener(v -> paymentSuccessHomeBtnSelected());
         String tkt_number= ccAvenueResponse.getTicket_number();
        String spittedvalue[]=tkt_number.split("-");
        ((TextView) view.findViewById(R.id.tv_ticket_number_field)).setText(spittedvalue[1]);
        if(busDetails.getBus_type_name().equalsIgnoreCase("")){
            ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText("NA");

        }else {
            ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busDetails.getBus_type_name());
        }

        ((TextView) view.findViewById(R.id.tv_total_persons_bus_fare_price_field)).setText(ccAvenueResponse.getFare());
        ((TextView) view.findViewById(R.id.tv_total_persons_service_charge_or_gst_field)).setText(ccAvenueResponse.getService_charge()+" %");
        ((TextView) view.findViewById(R.id.tv_total_persons_total_fare_field)).setText("Rs. "+ccAvenueResponse.getTotal_fare());

        ((TextView) view.findViewById(R.id.date_field)).setText(ccAvenueResponse.getBooking_date());
        ((TextView) view.findViewById(R.id.time_field)).setText(ccAvenueResponse.getBooking_time());

        if (ccAvenueResponse.getStatus().equalsIgnoreCase("success")) {
            ivBookingStatusField.setImageResource(R.drawable.booking_success_icon);
            paymentSuccessBtnLayoutField.setVisibility(View.VISIBLE);
            tvBookingStatusField.setText(getString(R.string.booking_successful));
            bookingSuccessLayoutField.setVisibility(View.VISIBLE);
            bookingFailedLayoutField.setVisibility(View.GONE);

            String date = DateUtilities.getTodayDateString(CALENDAR_DATE_FORMAT_THREE);
            String userId = MyProfile.getInstance().getUserId();
            MyProfile.getInstance().updateLiveTickets(progressDialog);


        } else {
            ivBookingStatusField.setImageResource(R.drawable.close_icon);
            paymentSuccessBtnLayoutField.setVisibility(View.GONE);
            tvBookingStatusField.setText(getString(R.string.payment_failed));
            bookingSuccessLayoutField.setVisibility(View.GONE);
            bookingFailedLayoutField.setVisibility(View.VISIBLE);
        }

        if(busDetails != null) {
            String busRouteName = String.format("%s %s", busOperatorName.toUpperCase(), busDetails.getBusServiceNO());
            ((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);

            String busRoute = String.format("%s to %s", busDetails.getOriginStopName(), busDetails.getReqTillStopNM());
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
        ((TextView) view.findViewById(R.id.tv_bus_fare_price_field)).setText(passengerDetails);
    }

    private void paymentSuccessHomeBtnSelected() {
        requireActivity().finish();
    }

    public void clearBackStack() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(
                0);
        fragmentManager.popBackStack(entry.getId(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.executePendingTransactions();
    }

    private void paymentSuccessBookAnotherBtnSelected() {
        startActivity(new Intent(getActivity(), TicketBookingActivity.class));
        Objects.requireNonNull(getActivity()).finish();
        /*clearBackStack();
        mListener.goToHome();*/
    }

    private void paymentFailedHomeBtnSelected() {
        requireActivity().finish();
    }

    private void paymentFailedTryAgainBtnSelected() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}