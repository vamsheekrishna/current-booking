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
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.adapters.ConcessionAddPassengersAdapter;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ConfirmTicketFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_BUS_DETAILS = "BusDetails";
    private static final String ARG_BUS_OPERATOR_NAME = "BusOperatorName";

    private BusObject busDetails;
    private String busOperatorName;

    private TextView tvConcessionCodeField;
    private TextView tvAdultsTicketCountField;
    private TextView tvChildTicketCountField;

    private int adultsCount = 0;
    private int childCount = 0;
    private double adultTicketPrice = 0.0;
    private double childTicketPrice = 0.0;
    private TextView tvTotalFareField;
    private double totalFare = 0.0;
    private List<Concession> concessionList;
    private List<Concession> personsAddedList;
    private ConcessionAddPassengersAdapter addedPassengersAdapter;
    private RecyclerView addPassengerRecyclerField;
    private TicketBookingViewModel ticketBookingModule;

    public ConfirmTicketFragment() {
        // Required empty public constructor
    }

    OnTicketBookingListener mListener;

    public static ConfirmTicketFragment newInstance(String busOperatorName) {
        ConfirmTicketFragment fragment = new ConfirmTicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUS_OPERATOR_NAME, busOperatorName);
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

        concessionList = new ArrayList<>();
        personsAddedList = new ArrayList<>();
        busOperatorName = Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName();
        busDetails = ticketBookingModule.getSelectedBusObject().getValue();

        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        String fullName = MyProfile.getInstance().getFirstName() + " " + MyProfile.getInstance().getLastName();
        ((TextView) view.findViewById(R.id.full_name)).setText(fullName);
        ((TextView) view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        String dob = MyProfile.getInstance().getDob();
        if(!TextUtils.isEmpty(dob)) {
            ((TextView) view.findViewById(R.id.user_age_field)).setText("");
        }

        ticketBookingModule.getConcessionLiveData().observe(Objects.requireNonNull(getActivity()), concessions -> {
            if (concessions != null && !concessions.isEmpty()) {
                concessionList.addAll(concessions);
            }
        });

        String busRoute = String.format("%s to %s", busDetails.getOriginStopName(), busDetails.getLastStopName());
        //String busRouteName = String.format("%s %s", busOperatorName, busDetails.getRouteNumber());
        //((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);
        //((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busDetails.getBusTypeNM());

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
        tvTotalFareField.setText(String.valueOf(totalFare));

        view.findViewById(R.id.add_passenger_btn_field).setOnClickListener(v -> {
            addPassengerSelected();
        });

        view.findViewById(R.id.confirm).setOnClickListener(this);

        addPassengerRecyclerField = view.findViewById(R.id.passengers_recycler_field);
        addPassengerRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider_two)));
        addPassengerRecyclerField.addItemDecoration(divider);

        addedPassengersAdapter = new ConcessionAddPassengersAdapter(requireActivity(), personsAddedList, (pObject, mode) -> {

            if (mode.equalsIgnoreCase("delete")) {
                int index = personsAddedList.indexOf(pObject);
                if (index > -1) {
                    personsAddedList.remove(pObject);
                    addedPassengersAdapter.notifyItemRemoved(index);
                    int size = personsAddedList.size();
                    if (size == 0) {
                        addPassengerRecyclerField.setVisibility(View.GONE);
                    }
                }
            }
        });
        addPassengerRecyclerField.setAdapter(addedPassengersAdapter);
    }

    private void addPassengerSelected() {
        concessionList = ticketBookingModule.getConcessionLiveData().getValue();
        AddPassengersDialogView addPassengersDialog = AddPassengersDialogView.getInstance(concessionList);
        addPassengersDialog.setInterfaceClickListener((pObject, delete) -> {
            if (pObject instanceof Concession) {
                addPassengerRecyclerField.setVisibility(View.VISIBLE);
                personsAddedList.add((Concession) pObject);
                int size = personsAddedList.size();
                addedPassengersAdapter.notifyItemInserted(size);
            }
        });
        if (!requireActivity().isFinishing()) {
            addPassengersDialog.show(requireActivity().getSupportFragmentManager(), AddPassengersDialogView.class.getName());
        }
    }

    private void updateTicketPrice() {

        double adultsTotalFare = adultsCount * adultTicketPrice;
        double childTotalFare = childCount * childTicketPrice;
        totalFare = adultsTotalFare + childTotalFare;
        totalFare = Math.round(totalFare);
        tvTotalFareField.setText(String.valueOf(totalFare));
    }

    @Override
    public void onClick(View v) {
        mListener.goToPayment();
    }
}