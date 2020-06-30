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
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Satya Seshu on 22/06/20.
 */
public class PassengerDetailsFragment extends BaseFragment {
    private static final String ARG_BUS_TYPE = "BusType";

    private BusObject busDetails;
    private String busOperatorName;

    private List<Concession> concessionList;
    private List<Concession> personsAddedList;
    private ConcessionAddPassengersAdapter addedPassengersAdapter;
    private RecyclerView addPassengerRecyclerField;
    private TicketBookingViewModel ticketBookingModule;
    private String busType;

    public PassengerDetailsFragment() {
        // Required empty public constructor
    }

    private OnTicketBookingListener mListener;

    public static PassengerDetailsFragment newInstance(String busType) {
        PassengerDetailsFragment fragment = new PassengerDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUS_TYPE, busType);
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
        return inflater.inflate(R.layout.fragment_passenger_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        concessionList = new ArrayList<>();
        personsAddedList = new ArrayList<>();
        busOperatorName = Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName();
        busDetails = ticketBookingModule.getSelectedBusObject().getValue();

        Bundle extras = getArguments();
        if (extras != null) {
            busType = extras.getString(ARG_BUS_TYPE);
        }

        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        String fullName = String.format("%s %s", MyProfile.getInstance().getFirstName(), MyProfile.getInstance().getLastName());
        ((TextView) view.findViewById(R.id.full_name)).setText(fullName);
        ((TextView) view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        String dob = MyProfile.getInstance().getDob();
        if (!TextUtils.isEmpty(dob)) {
            ((TextView) view.findViewById(R.id.user_age_field)).setText("");
        }

        String busRouteName = String.format("%s %s", busOperatorName.toUpperCase(), busDetails.getBusServiceNo());
        ((TextView) view.findViewById(R.id.tv_route_name_field)).setText(busRouteName);
        ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText(busType);

        ticketBookingModule.getConcessionLiveData().observe(Objects.requireNonNull(getActivity()), concessions -> {
            if (concessions != null && !concessions.isEmpty()) {
                concessionList.addAll(concessions);
            }
        });

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
        String fareAmount = String.format("Rs. %s", busDetails.getFareAmt());
        ((TextView) view.findViewById(R.id.tv_bus_fare_price_field)).setText(fareAmount);

        view.findViewById(R.id.add_passenger_btn_field).setOnClickListener(v -> addPassengerSelected());

        addPassengerRecyclerField = view.findViewById(R.id.passengers_recycler_field);
        addPassengerRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider_two)));
        addPassengerRecyclerField.addItemDecoration(divider);

        addedPassengersAdapter = new ConcessionAddPassengersAdapter(requireActivity(), personsAddedList, pObject -> {
            if (pObject instanceof Concession) {
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
        view.findViewById(R.id.confirm_payment).setOnClickListener(v -> confirmPaymentSelected());
    }

    private void confirmPaymentSelected() {
        if(!personsAddedList.isEmpty()) {

            /*JsonArray result = (JsonArray) new Gson().toJsonTree(personsAddedList,
                    new TypeToken<List<Concession>>() {
                    }.getType());
            gson.toJson(response);*/

            Type listType = new TypeToken<List<Concession>>() {}.getType();
            /*List<String> target = new A<String>();
            target.add("blah");*/

            Gson gson = new Gson();
            String jsonText = gson.toJson(personsAddedList, listType);
            List<Concession> target2 = gson.fromJson(jsonText, listType);


            progressDialog.show();
            TicketBookingServices ticketBookingServices = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
            ticketBookingServices.getFare(ticketBookingModule.getSelectedBusOperator().getValue().getOperatorCode(),
                    "",
                    ticketBookingModule.getSelectedPickUpPoint().getValue().getStopCode(),
                    ticketBookingModule.getSelectedDropPoint().getValue().getStopCode(),
                    ticketBookingModule.getSelectedBusType().getValue().getBusTypeCD(),
                    ticketBookingModule.getSelectedBusObject().getValue().getBusServiceNo(),
                    jsonText
            ).enqueue(new Callback<GetFareResponse>() {
                @Override
                public void onResponse(Call<GetFareResponse> call, Response<GetFareResponse> response) {
                    if(response.isSuccessful()) {
                        GetFareResponse fareDetails = response.body();
                        if(response.body().getStatus().equalsIgnoreCase("success")) {
                            showDialog("", fareDetails.getMsg());
                            mListener.gotoConfirmTicket(busType, fareDetails.getFareDetails().getFareDetails());
                        } else {
                            showDialog("", fareDetails.getMsg());
                        }
                    } else {}
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetFareResponse> call, Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog("", getString(R.string.please_enter_passenger_details));
        }
    }

    private void addPassengerSelected() {
        concessionList = ticketBookingModule.getConcessionLiveData().getValue();
        AddPassengersDialogView addPassengersDialog = AddPassengersDialogView.getInstance(concessionList);
        addPassengersDialog.setInterfaceClickListener(pObject -> {
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
}