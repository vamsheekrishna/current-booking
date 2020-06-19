package com.currentbooking.ticketbooking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.currentbooking.ticketbooking.adapters.ConcessionsTypeAdapter;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfirmTicketFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_BUS_DETAILS = "BusDetails";
    private static final String ARG_BUS_OPERATOR_NAME = "BusOperatorName";

    private BusObject busDetails;
    private String busOperatorName;

    private TextView tvConcessionCodeField;
    private TextView tvAdultsTicketPriceField;
    private TextView tvChildTicketPriceField;
    private TextView tvAdultsTicketCountField;
    private TextView tvChildTicketCountField;

    private int adultsCount = 0;
    private int childCount = 0;
    private double adultTicketPrice = 0.0;
    private double childTicketPrice = 0.0;
    private TextView tvTotalFareField;
    private double totalFare = 0.0;
    private TicketBookingViewModel ticketBookingModule;
    private List<Concession> concessionList;

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

        ticketBookingModule = new ViewModelProvider(this).get(TicketBookingViewModel.class);
        concessionList = new ArrayList<>();
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
        ((TextView) view.findViewById(R.id.first_name)).setText(MyProfile.getInstance().getFirstName());
        ((TextView) view.findViewById(R.id.last_name)).setText(MyProfile.getInstance().getLastName());
        ((TextView) view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView) view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        AppCompatTextView dob = view.findViewById(R.id.dob);
        dob.setText(MyProfile.getInstance().getDob());
        ((TextView) view.findViewById(R.id.address1)).setText(MyProfile.getInstance().getAddress1());
        ((TextView) view.findViewById(R.id.address2)).setText(MyProfile.getInstance().getAddress2());
        ((TextView) view.findViewById(R.id.state)).setText(MyProfile.getInstance().getState());
        ((TextView) view.findViewById(R.id.pin_code)).setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.edit_profile).setVisibility(View.GONE);
        view.findViewById(R.id.conform).setOnClickListener(this);

        String busRoute = String.format("%s to %s", busDetails.getSourceStageName(), busDetails.getDestinationStageName());
        String busRouteName = String.format("%s %s", busOperatorName, busDetails.getRouteNumber());
        ((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);
        ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busDetails.getBusTypeNM());
        ((TextView) view.findViewById(R.id.tv_bus_route_field)).setText(busRoute);
        ((TextView) view.findViewById(R.id.tv_bus_journey_start_time_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_journey_hours_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_journey_end_time_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_fare_price_field)).setText("");
        view.findViewById(R.id.btn_book_now_field).setVisibility(View.GONE);
        tvTotalFareField = view.findViewById(R.id.tv_total_fare_field);
        tvTotalFareField.setText(String.valueOf(totalFare));

        view.findViewById(R.id.confirm).setOnClickListener(this);

        Button btnConcessionCodeField = view.findViewById(R.id.btn_concession_code_field);
        LinearLayout concessionCodeLayoutField = view.findViewById(R.id.concession_code_apply_layout_field);
        ImageView ivDeleteConcessionCodeField = view.findViewById(R.id.delete_concession_code_btn_field);
        tvConcessionCodeField = view.findViewById(R.id.tv_concession_code_field);
        btnConcessionCodeField.setOnClickListener(v -> {
            btnConcessionCodeField.setVisibility(View.GONE);
            concessionCodeLayoutField.setVisibility(View.VISIBLE);
            gotoConcessionScreen();
        });
        ivDeleteConcessionCodeField.setOnClickListener(v -> {
            tvConcessionCodeField.setText("");
            ivDeleteConcessionCodeField.setVisibility(View.GONE);
            concessionCodeLayoutField.setVisibility(View.GONE);
            btnConcessionCodeField.setVisibility(View.VISIBLE);
        });

        adultTicketPrice = 300;
        childTicketPrice = 150;

        tvAdultsTicketCountField = view.findViewById(R.id.tv_no_of_adults_field);
        tvChildTicketCountField = view.findViewById(R.id.tv_no_of_childs_field);
        tvAdultsTicketPriceField = view.findViewById(R.id.tv_adult_ticket_price_field);
        tvChildTicketPriceField = view.findViewById(R.id.tv_child_ticket_price_field);

        tvAdultsTicketCountField.setText(String.valueOf(adultsCount));
        tvChildTicketCountField.setText(String.valueOf(childCount));

        TextView tvAdultDecrementField = view.findViewById(R.id.tv_adult_dec_field);
        TextView tvAdultIncrementField = view.findViewById(R.id.tv_adult_inc_field);

        tvAdultDecrementField.setOnClickListener(v -> {
            if (adultsCount > 0) {
                adultsCount--;
                tvAdultsTicketCountField.setText(String.valueOf(adultsCount));
                updateTicketPrice();
            }
        });
        tvAdultIncrementField.setOnClickListener(v -> {
            if (adultsCount >= 0 && adultsCount <= 10) {
                adultsCount++;
                tvAdultsTicketCountField.setText(String.valueOf(adultsCount));
                updateTicketPrice();
            }
        });

        TextView tvChildDecrementField = view.findViewById(R.id.tv_child_dec_field);
        TextView tvChildIncrementField = view.findViewById(R.id.tv_child_inc_field);

        tvChildDecrementField.setOnClickListener(v -> {
            if (childCount > 0) {
                childCount--;
                tvChildTicketCountField.setText(String.valueOf(childCount));
                updateTicketPrice();
            }
        });
        tvChildIncrementField.setOnClickListener(v -> {
            if (childCount >= 0 && childCount <= 10) {
                childCount++;
                tvChildTicketCountField.setText(String.valueOf(childCount));
                updateTicketPrice();
            }
        });
    }

    private void updateTicketPrice() {

        double adultsTotalFare = adultsCount * adultTicketPrice;
        double childTotalFare = childCount * childTicketPrice;
        totalFare = adultsTotalFare + childTotalFare;
        totalFare = Math.round(totalFare);
        tvTotalFareField.setText(String.valueOf(totalFare));
    }

    private void gotoConcessionScreen() {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()), R.style.Theme_AppCompat_Dialog);
        View view = View.inflate(getActivity(), R.layout.concession_list_view, null);
        dialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.concession_list_field);
        recyclerView.setHasFixedSize(false);
        ConcessionsTypeAdapter concessionsTypeAdapter = new ConcessionsTypeAdapter(getActivity(), concessionList);
        recyclerView.setAdapter(concessionsTypeAdapter);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        mListener.goToPayment();
    }
}