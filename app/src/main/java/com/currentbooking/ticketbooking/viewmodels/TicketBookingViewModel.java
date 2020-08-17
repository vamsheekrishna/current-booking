package com.currentbooking.ticketbooking.viewmodels;

import android.app.Dialog;
import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;
import com.currentbooking.utilits.cb_api.responses.BusPoint;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.cb_api.responses.BusType;
import com.currentbooking.utilits.cb_api.responses.BusTypeList;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.cb_api.responses.ConcessionListResponse;
import com.currentbooking.utilits.cb_api.responses.ConcessionRates;
import com.currentbooking.utilits.views.CustomLoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingViewModel extends ViewModel {
    private final TicketBookingServices ticketBookingServices;

    private MutableLiveData<ArrayList<BusOperator>> busOperators;

    private MutableLiveData<ArrayList<BusType>> busTypes;
    private MutableLiveData<BusType> selectBusType;

    private MutableLiveData<ArrayList<BusPoint>> busPoints = new MutableLiveData<>();

    private MutableLiveData<BusStopObject> selectedPickUpPoint = new MutableLiveData<>();
    private MutableLiveData<BusStopObject> selectedDropPoint = new MutableLiveData<>();

    private MutableLiveData<BusObject> selectedBusObject = new MutableLiveData<>();
    private MutableLiveData<BusOperator> selectedBusOperator = new MutableLiveData<>();

    private MutableLiveData<Concession> selectedConcession = new MutableLiveData<>();
    //private MutableLiveData<ConcessionRates> selectedConcessionRate = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Concession>> concessionList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ConcessionRates>> concessionRates = new MutableLiveData<>();
    private Dialog loadingDialog;
    private MvvmView.View mvvmView;
    public ObservableField<String>  searchHintField;


    public TicketBookingViewModel(MvvmView.View mvvmView) {
        this.mvvmView = mvvmView;
        ticketBookingServices = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        searchHintField = new ObservableField<>("");
        getBusOperators();
    }

    public LiveData<ArrayList<BusPoint>> getBusPoints() {
        return busPoints;
    }

    public MutableLiveData<BusStopObject> getSelectedPickUpPoint() {
        return selectedPickUpPoint;
    }
    public MutableLiveData<BusStopObject> getSelectedDropPoint() {
        return selectedDropPoint;
    }

    public LiveData<ArrayList<BusOperator>> getBusOperators() {
        if (busOperators == null) {
            busOperators = new MutableLiveData<>();
            loadingDialog = CustomLoadingDialog.getInstance(mvvmView.getContext());
            loadBusOperators();
        }
        return busOperators;
    }

    public LiveData<ArrayList<BusType>> getBusTypes() {
        if(busTypes == null) {
            busTypes = new MutableLiveData<>();
            loadBusTypes();
        }
        return busTypes;
    }

    public MutableLiveData<BusOperator> getSelectedBusOperator() {
        if (selectedBusOperator == null) {
            selectedBusOperator = new MutableLiveData<>();
        }
        return selectedBusOperator;
    }

    public MutableLiveData<BusObject> getSelectedBusObject() {
        return selectedBusObject;
    }
    private void loadBusOperators() {
        loadingDialog.show();
        ticketBookingServices.getBusOperators().enqueue(new Callback<BusOperatorList>() {
            @Override
            public void onResponse(@NotNull Call<BusOperatorList> call, @NotNull Response<BusOperatorList> response) {
                loadingDialog.dismiss();
                if (response.isSuccessful()) {
                    LoggerInfo.printLog("operators list response", response.body());
                    BusOperatorList data = response.body();
                    if (data != null) {
                        if (data.getStatus().equals("success")) {
                            if (null != data.getBusOperatorList().getBusOperators()) {
                                busOperators.setValue(data.getBusOperatorList().getBusOperators());
                            }
                        } else {
                            busOperators.setValue(new ArrayList<>());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BusOperatorList> call, @NotNull Throwable t) {
                loadingDialog.dismiss();
                busOperators.setValue(new ArrayList<>());
            }
        });
    }

    public void loadBusTypes() {
        if (selectedBusOperator != null) {
            BusOperator busOperator = selectedBusOperator.getValue();
            if (busOperator != null) {
                String operatorCode = busOperator.getOperatorCode();
                if (!TextUtils.isEmpty(operatorCode)) {
                    loadingDialog.show();
                    ticketBookingServices.getBusTypes(operatorCode.toLowerCase()).enqueue(new Callback<BusTypeList>() {
                        @Override
                        public void onResponse(@NotNull Call<BusTypeList> call, @NotNull Response<BusTypeList> response) {
                            loadingDialog.dismiss();
                            if (response.isSuccessful()) {
                                BusTypeList data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equals("success")) {
                                        if (null != data.getBusTypes().getBusTypes()) {
                                            busTypes.setValue(data.getBusTypes().getBusTypes());
                                        }
                                    } else {
                                        busTypes.setValue(new ArrayList<>());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<BusTypeList> call, @NotNull Throwable t) {
                            loadingDialog.dismiss();
                            busTypes.setValue(new ArrayList<>());
                        }
                    });
                }
            }
        }

    }

    public MutableLiveData<BusType>  getSelectedBusType() {
        if(null == selectBusType) {
            selectBusType = new MutableLiveData<>();
        }
        return selectBusType;
    }

    public void onBusOperatorChanged() {
        resetBusType();
        resetBusPointData();
        BusOperator busOperator = selectedBusOperator.getValue();
        if (busOperator != null) {
            String operatorCode = busOperator.getOperatorCode();
            if (!TextUtils.isEmpty(operatorCode)) {
                getConcession(operatorCode);
            }
        }
    }

    public MutableLiveData<ArrayList<Concession>>  getConcessionLiveData() {
        if(concessionList == null) {
            concessionList = new MutableLiveData<>();
        }
        return concessionList;
    }

    public void getConcession(String selectedOperator) {
        ticketBookingServices.getConcessionList(selectedOperator).enqueue(new Callback<ConcessionListResponse>() {
            @Override
            public void onResponse(@NotNull Call<ConcessionListResponse> call, @NotNull Response<ConcessionListResponse> response) {
                if (response.isSuccessful()) {
                    ConcessionListResponse concessionListResponse = response.body();
                    if (concessionListResponse != null) {
                        if (concessionListResponse.getStatus().equalsIgnoreCase("success")) {
                            ArrayList<Concession> data = concessionListResponse.getConcessionList().getConcessions();
                            concessionList.setValue(data);
                        } else {
                            ArrayList<Concession> data = new ArrayList<>();
                            concessionList.setValue(data);
                        }
                    }

                    selectedConcession.setValue(new Concession());
                }
                loadBusTypes();
            }

            @Override
            public void onFailure(@NotNull Call<ConcessionListResponse> call, @NotNull Throwable t) {
                ArrayList<Concession> data = new ArrayList<>();
                concessionList.setValue(data);
                selectedConcession.setValue(new Concession());
                loadBusTypes();
            }
        });
    }

    public void resetBusType() {
        busTypes.setValue(new ArrayList<>());
        selectBusType.setValue(new BusType());
    }
    public void resetBusPointData() {
        selectedPickUpPoint.setValue(new BusStopObject());
        selectedDropPoint.setValue(new BusStopObject());
        busPoints.setValue(new ArrayList<>());
    }
}
