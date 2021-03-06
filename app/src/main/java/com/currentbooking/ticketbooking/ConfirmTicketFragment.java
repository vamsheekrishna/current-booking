package com.currentbooking.ticketbooking;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.MyViewModelFactory;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ConfirmTicketFragment extends BaseFragment implements MvvmView.View {
    private static final String ARG_BUS_TYPE = "BusType";
    private static final String ARG_ADDED_PASSENGERS_LIST = "AddedPassengersList";

    private BusObject busDetails;
    private String busOperatorName;
    private String busType;

    private GetFareResponse.FareDetails mFareDetails;
    private TicketBookingViewModel ticketBookingModule;
    private String passengerDetails;


    public ConfirmTicketFragment() {
        // Required empty public constructor
    }

    private OnTicketBookingListener mListener;

    public static ConfirmTicketFragment newInstance(String busType, GetFareResponse.FareDetails personsAddedList) {
        ConfirmTicketFragment fragment = new ConfirmTicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUS_TYPE, busType);
        args.putSerializable(ARG_ADDED_PASSENGERS_LIST, personsAddedList);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Confirm Ticket");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity()), new MyViewModelFactory(this)).get(TicketBookingViewModel.class);
        return inflater.inflate(R.layout.fragment_confirm_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        busOperatorName = Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName();
        busDetails = ticketBookingModule.getSelectedBusObject().getValue();

        Bundle extras = getArguments();
        if (extras != null) {
            busType = extras.getString(ARG_BUS_TYPE);
            mFareDetails = (GetFareResponse.FareDetails) extras.getSerializable(ARG_ADDED_PASSENGERS_LIST);
        }

        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        String busRoute = String.format("%s to %s", busDetails.getOriginStopName(), busDetails.getReqTillStopNM());
        String busRouteName = String.format("%s %s", "MSRTC", busDetails.getBusServiceNO());
        ((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);
        ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busDetails.getBus_type_name());

        Calendar journeyStartCalendar = DateUtilities.getCalendarFromDate(busDetails.getOriginDateTime());
        Calendar journeyEndCalendar = DateUtilities.getCalendarFromDate(busDetails.getLastStopDateTime());
        String startTime = DateUtilities.getTimeFromCalendar(journeyStartCalendar);
        String endTime = DateUtilities.getTimeFromCalendar(journeyEndCalendar);
        String hoursDifference = String.format("%s Hrs", DateUtilities.getJourneyHours(journeyStartCalendar, journeyEndCalendar));

        ((TextView) view.findViewById(R.id.tv_bus_route_field)).setText(busRoute);
        ((TextView) view.findViewById(R.id.tv_bus_journey_start_time_field)).setText(startTime);
        ((TextView) view.findViewById(R.id.tv_bus_journey_hours_field)).setText(hoursDifference);
        ((TextView) view.findViewById(R.id.tv_bus_journey_end_time_field)).setText(endTime);
        String fareAmount = String.format("Rs. %s", busDetails.getFareAmt());

        ((TextView) view.findViewById(R.id.tv_bus_fare_price_field)).setText(fareAmount);
        TextView tvTotalFareField = view.findViewById(R.id.tv_total_fare_field);
        tvTotalFareField.setText(String.valueOf("Rs. "+mFareDetails.getTotal()));

        TextView tvFareField = view.findViewById(R.id.tv_fare_field);
        tvFareField.setText("Rs. "+mFareDetails.getFare());
        TextView tvTaxesFareField = view.findViewById(R.id.tv_service_charge_or_gst_field);
        tvTaxesFareField.setText(mFareDetails.getServiceCharge()+" %");
        TextView tvPassengersDetailsField = view.findViewById(R.id.tv_passengers_details_field);

        int adultsCount = mFareDetails.getTotalAdultSeat();
        int childCount = mFareDetails.getTotalChildSeat();
        String adultsDetails = "";
        if (adultsCount > 0) {
            if (adultsCount == 1)
                adultsDetails = String.format(Locale.getDefault(), "%d %s", adultsCount, getString(R.string.adult));
            else
                adultsDetails = String.format(Locale.getDefault(), "%d %s", adultsCount, getString(R.string.adults));

        }
        String childDetails = "";
        if(childCount > 0) {
            if(adultsDetails.length()>1) {
                adultsDetails = adultsDetails+", ";
            }
            adultsDetails = adultsDetails + String.format(Locale.getDefault(), "%d %s", childCount, getString(R.string.children));
        }

        if(!TextUtils.isEmpty(childDetails)) {
            passengerDetails = String.format("%s,\n%s", adultsDetails, childDetails);
        } else {
            passengerDetails = adultsDetails;
        }
        char firstChar = passengerDetails.charAt(0);
        int length = passengerDetails.length();
        if(firstChar == ',') {
            passengerDetails = passengerDetails.substring(2, length);
        }
        length = passengerDetails.length();
        char lastChar = passengerDetails.charAt(length - 2);
        if (lastChar == ',') {
            passengerDetails = passengerDetails.substring(0, length - 2);
        }
        tvPassengersDetailsField.setText(passengerDetails);
        view.findViewById(R.id.confirm_payment).setOnClickListener(v -> {
            confirmSelected();
        });
        TextView tvTicketExpiryMessageField = view.findViewById(R.id.tv_ticket_expiry_message_field);

        String noteMessage = "<font color='#EF4B4E'>Note*</font>";
        tvTicketExpiryMessageField.setText(HtmlCompat.fromHtml(
                String.format("%s %s", noteMessage, getString(R.string.ticket_expire_message)),
                HtmlCompat.FROM_HTML_MODE_LEGACY));

    }

    private void confirmSelected() {
        int amountValue = Utils.getIntegerValueFromString((mFareDetails.getFare()));
        if(amountValue>0)
        mListener.gotoPayment(passengerDetails, mFareDetails);
        else{
            showDialog("Can not booked ticket with zero fare");
        }
    }
}