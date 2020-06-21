package com.currentbooking.ticketbooking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.models.ConcessionTypeModel;
import com.currentbooking.ticketbooking.adapters.ConcessionAddPassengersAdapter;
import com.currentbooking.ticketbooking.adapters.ConcessionsTypeAdapter;
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
    private List<ConcessionTypeModel> concessionTypeModelList;
    private ConcessionAddPassengersAdapter concessionAddPassengersAdapter;
    private RecyclerView addPassengerRecyclerField;

    public ConfirmTicketFragment() {
        // Required empty public constructor
    }

    OnTicketBookingListener mListener;

    public static ConfirmTicketFragment newInstance(BusObject busDetails, String busOperatorName) {
        ConfirmTicketFragment fragment = new ConfirmTicketFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BUS_DETAILS, busDetails);
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
        return inflater.inflate(R.layout.fragment_confirm_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TicketBookingViewModel ticketBookingModule = new ViewModelProvider(this).get(TicketBookingViewModel.class);
        concessionList = new ArrayList<>();
        concessionTypeModelList = new ArrayList<>();
        ticketBookingModule.getConcessionLiveData().observe(Objects.requireNonNull(getActivity()), concessions -> {
            if (concessions != null && !concessions.isEmpty()) {
                concessionList.addAll(concessions);
            }
        });

        if (getArguments() != null) {
            busDetails = (BusObject) getArguments().getSerializable(ARG_BUS_DETAILS);
            busOperatorName = getArguments().getString(ARG_BUS_OPERATOR_NAME);
        }

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

        Button btnAddPassengerField = view.findViewById(R.id.add_passenger_btn_field);
        btnAddPassengerField.setOnClickListener(v -> {
            addPassengerSelected();
        });

        view.findViewById(R.id.confirm).setOnClickListener(this);

        addPassengerRecyclerField = view.findViewById(R.id.passengers_recycler_field);
        addPassengerRecyclerField.setHasFixedSize(false);

        concessionAddPassengersAdapter = new ConcessionAddPassengersAdapter(requireActivity(), concessionTypeModelList, pObject -> {
            if (pObject instanceof ConcessionTypeModel) {
                int index = concessionTypeModelList.indexOf(pObject);
                if (index > -1) {
                    concessionTypeModelList.remove(pObject);
                    concessionAddPassengersAdapter.notifyItemRemoved(index);
                    int size = concessionTypeModelList.size();
                    if (size == 0) {
                        addPassengerRecyclerField.setVisibility(View.GONE);
                    }
                }
            }
        });
        addPassengerRecyclerField.setAdapter(concessionAddPassengersAdapter);
    }

    private void addPassengerSelected() {
        AddPassengersDialogView addPassengersDialog = AddPassengersDialogView.getInstance(concessionList);
        addPassengersDialog.setInterfaceClickListener(pObject -> {
            if (pObject instanceof ConcessionTypeModel) {
                addPassengerRecyclerField.setVisibility(View.VISIBLE);
                concessionTypeModelList.add((ConcessionTypeModel) pObject);
                int size = concessionTypeModelList.size();
                concessionAddPassengersAdapter.notifyItemInserted(size);
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