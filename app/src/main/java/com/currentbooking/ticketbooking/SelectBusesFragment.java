package com.currentbooking.ticketbooking;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.databinding.FragmentSelectBusBinding;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.ticketbooking.adapters.SelectBusesAdapter;
import com.currentbooking.ticketbooking.viewmodels.SelectBusesViewModel;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.MyViewModelFactory;
import com.currentbooking.utilits.NetworkUtility;
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


public class SelectBusesFragment extends BaseFragment implements MvvmView.View {

    private static final String ARG_BUS_OPERATOR_NAME = "BusOperatorName";
    private static final String ARG_BUS_TYPE_NAME = "BusTypeName";

    private TicketBookingViewModel ticketBookingModule;
    private SelectBusesAdapter selectBusesAdapter;
    private RecyclerView busesResultListField;
    OnTicketBookingListener mListener;
    private String busOperatorName;
    private String busTypeName;

    public SelectBusesFragment() {
        // Required empty public constructor
    }

    public static SelectBusesFragment newInstance(String busOperatorName, String busTypeName) {
        SelectBusesFragment fragment = new SelectBusesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUS_OPERATOR_NAME, busOperatorName);
        args.putString(ARG_BUS_TYPE_NAME, busTypeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            busOperatorName = getArguments().getString(ARG_BUS_OPERATOR_NAME);
            busTypeName = getArguments().getString(ARG_BUS_TYPE_NAME);
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
        //ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity()), new MyViewModelFactory(this)).get(TicketBookingViewModel.class);


        //busTypeName =  Objects.requireNonNull(Objects.requireNonNull(ticketBookingModule.getSelectedBusType().getValue()).getBusTypeCD());

        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(NetworkUtility.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            TicketBookingServices busListService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
            busOperatorName = Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOperatorCode();
            busListService.getAvailableBusList(busOperatorName,
                    busTypeName,
                    Objects.requireNonNull(ticketBookingModule.getSelectedPickUpPoint().getValue()).getStopCode(),
                    Objects.requireNonNull(ticketBookingModule.getSelectedDropPoint().getValue()).getStopCode()).enqueue(new Callback<AvailableBusList>() {
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
                                            ticketBookingModule.getSelectedBusObject().setValue(busObject);
                                            ticketBookingModule.concessionbaseonbustypr(busObject.getBusTypeCD());

                                            mListener.gotoPassengerDetails(busTypeName);
                                        }, getActivity(), busesList, Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName(), busTypeName);
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
        }
    }

    /* private void setDummyData() {
        ArrayList<BusObject> busesList = new ArrayList<>();
        BusObject busObject = new BusObject();

        busObject.setOriginStopName("MUMBAI CENTRAL");
        busObject.setOriginDateTime("2020-06-20 15:00:00");
        busObject.setLastStopCd("SWR");
        busObject.setLastStopName("SWARGATE, PUNE");
        busObject.setLastStopDateTime("2020-06-20 20:00:00");
        busObject.setReqFromStopCd("MCT");
        busObject.setReqFromStopNm("MUMBAI CENTRAL");
        busObject.setReqFromDateTime("2020-06-20 15:00:00");
        busObject.setReqTillStopCd("SWR");
        busObject.setReqTillStopNm("SWARGATE, PUNE");
        busObject.setReqTillDateTime("2020-06-20 20:00:00");
        busObject.setDepotNm("THN");
        busObject.setBusServiceNo("507559");
        busObject.setFareAmt(282.80);
        busesList.add(busObject);

        selectBusesAdapter = new SelectBusesAdapter(v -> {
            BusObject busObjectDetails = (BusObject) v.getTag();
            mListener.goToConfirmTicket(busOperatorName, busObjectDetails);
        }, getActivity(), busesList, "MSRTC", busTypeName);
        busesResultListField.setAdapter(selectBusesAdapter);
    }*/

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
            showCloseDialog(getString(R.string.no_information_available));
        }
    }

    private void showCloseDialog(String message) {
        showDialog(getString(R.string.message), message, new CallBackInterface() {
            @Override
            public void callBackReceived(Object pObject) {
                Log.d("BhuvanMithileshVidhita", "show closed dialog");
                //requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.showBadge(true);
        mListener.showHamburgerIcon(true);
    }
}