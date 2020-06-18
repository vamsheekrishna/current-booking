package com.currentbooking.ticketbooking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.databinding.FragmentSelectBusBinding;
import com.currentbooking.ticketbooking.adapters.SelectBusesAdapter;
import com.currentbooking.ticketbooking.viewmodels.SelectBusesViewModel;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.AvailableBusList;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectBusesFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_BUS_OPERATOR_NAME = "BusOperatorName";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TicketBookingServices busListService;
    private TicketBookingViewModel ticketBookingModule;
    private SelectBusesAdapter selectBusesAdapter;
    private RecyclerView busesResultListField;
    OnTicketBookingListener mListener;
    private String busOperatorName;

    public SelectBusesFragment() {
        // Required empty public constructor
    }

    public static SelectBusesFragment newInstance(String busOperatorName, String param2) {
        SelectBusesFragment fragment = new SelectBusesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUS_OPERATOR_NAME, busOperatorName);
        args.putString(ARG_PARAM2, param2);
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
        if (getArguments() != null) {
            busOperatorName = getArguments().getString(ARG_BUS_OPERATOR_NAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.select_bus));
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initDataBinding(inflater);
    }

    private View initDataBinding(@NotNull LayoutInflater inflater) {
        FragmentSelectBusBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_bus,
                null, false);
        SelectBusesViewModel selectBusesViewModel = new SelectBusesViewModel();
        dataBinding.setViewModel(selectBusesViewModel);

        loadUIComponents(dataBinding);

        progressDialog.show();
        busListService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
        /*ticketBookingModule.getSelectedBusOperator().getValue().opertorName.toLowerCase(),
                ticketBookingModule.getSelectedBusType().getValue().getBusTypeCD(),*/
        busOperatorName = "msrtc";
        busListService.getAvailableBusList(busOperatorName,
                "SC",
                "PNL",
                "TALOJA").enqueue(new Callback<AvailableBusList>() {
            @Override
            public void onResponse(@NotNull Call<AvailableBusList> call, @NotNull Response<AvailableBusList> response) {
                if (response.isSuccessful()) {
                    AvailableBusList availableBusList = response.body();
                    if (availableBusList != null) {
                        if (availableBusList.getStatus().equals("success")) {
                            AvailableBusList.BusListObj busListObj = availableBusList.getBusListObj();
                            if (busListObj != null) {
                                ArrayList<BusObject> busesList = busListObj.getBusList();
                                if (busesList != null && !busesList.isEmpty()) {
                                    selectBusesAdapter = new SelectBusesAdapter(v -> {
                                        BusObject busObject = (BusObject) v.getTag();
                                        mListener.goToConfirmTicket(busOperatorName, busObject);
                                    }, getActivity(), busesList, Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName());
                                }
                            }
                        }
                    }
                }
                progressDialog.dismiss();
                setBusesAdapter();
            }

            @Override
            public void onFailure(@NotNull Call<AvailableBusList> call, @NotNull Throwable t) {
                showDialog("", t.getMessage());
                progressDialog.dismiss();
            }
        });

        return dataBinding.getRoot();
    }

    private void loadUIComponents(FragmentSelectBusBinding dataBinding) {
        busesResultListField = dataBinding.busesResultsListField;
        busesResultListField.setHasFixedSize(false);
        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                R.drawable.recycler_decoration_divider_two)));
        busesResultListField.addItemDecoration(divider);
    }

    private void setBusesAdapter() {
        if (selectBusesAdapter != null) {
            busesResultListField.setAdapter(selectBusesAdapter);
        } else {
            showDialog("", getString(R.string.no_information_available));
        }
    }
}