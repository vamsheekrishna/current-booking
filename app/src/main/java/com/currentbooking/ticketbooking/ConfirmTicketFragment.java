package com.currentbooking.ticketbooking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ConfirmTicketFragment extends BaseFragment {
    private static final String ARG_BUS_TYPE = "BusType";
    private static final String ARG_ADDED_PASSENGERS_LIST = "AddedPassengersList";

    private BusObject busDetails;
    private String busOperatorName;
    private String busType;

    private GetFareResponse.FareDetails mFareDetails;
    private TicketBookingViewModel ticketBookingModule;
    private TextView tvTotalFareField;
    private TextView tvFareField;
    private TextView tvTaxesFareField;
    private TextView tvPassengersDetailsField;

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Confirm Ticket");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
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
        String busRoute = String.format("%s to %s", busDetails.getOriginStopName(), busDetails.getLastStopName());
        String busRouteName = String.format("%s %s", busOperatorName.toUpperCase(), busDetails.getBusServiceNo());
        ((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);
        ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busType);

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
        tvTotalFareField = view.findViewById(R.id.tv_total_fare_field);
        tvTotalFareField.setText(String.valueOf(mFareDetails.getTotal()));

        tvFareField = view.findViewById(R.id.tv_fare_field);
        tvTaxesFareField = view.findViewById(R.id.tv_service_charge_or_gst_field);

        tvPassengersDetailsField = view.findViewById(R.id.tv_passengers_details_field);

        int adultsCount = mFareDetails.getTotalAdultSeat();
        int childCount = mFareDetails.getTotalChildSeat();
        int srCitizensCount = 0;
        /*for( FareBreakup concessionDetails : personsAddedList.getFareDetails().getFareBreakups()) {
            if(concessionDetails.getLabel().equals(getString(R.string.adult))) {
                adultsCount += 1;
            } else if(concessionDetails.getPersonType().equals(getString(R.string.child))) {
                childCount += 1;
            } else if(concessionDetails.getPersonType().equals(getString(R.string.sr_citizen))) {
                srCitizensCount += 1;
            }
        }*/

        String adultsDetails = "";
        if(adultsCount != 0) {
            if(adultsCount == 1) adultsDetails = String.format(Locale.getDefault(), "%d %s", adultsCount, getString(R.string.adult));
            else adultsDetails = String.format(Locale.getDefault(), "%d %s", adultsCount, getString(R.string.adults));

        }
        //String childDetails = "";
        if(childCount > 0) {
            if(adultsDetails.length()>1) {
                adultsDetails = adultsDetails+", ";
            }
            adultsDetails = adultsDetails + String.format(Locale.getDefault(), "%d %s", childCount, getString(R.string.children));
        }
        /*String srCitizenDetails = "";
        if(srCitizensCount != 0) {
            if(srCitizensCount == 1) srCitizenDetails = String.format(Locale.getDefault(), "%d %s", srCitizensCount, getString(R.string.sr_citizen));
            else srCitizenDetails = String.format(Locale.getDefault(), "%d %s", srCitizensCount, getString(R.string.sr_citizens));
        }*/

        /*String passengerDetails = "";
        if(!TextUtils.isEmpty(childDetails)) {
            passengerDetails = String.format("%s,\n%s,\n%s", adultsDetails, childDetails, srCitizenDetails);
        } else {
            passengerDetails = String.format("%s,\n%s", adultsDetails, srCitizenDetails);
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
        }*/
        tvPassengersDetailsField.setText(adultsDetails);
        view.findViewById(R.id.confirm_payment).setOnClickListener(v -> confirmSelected());
    }


    private void confirmSelected() {
        mListener.gotoPayment(mFareDetails);
        // mListener.gotoTicketStatus(true, tvPassengersDetailsField.getText().toString(), "success");
    }
}