package com.currentbooking.ticketbooking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;
import com.currentbooking.utilits.cb_api.responses.BusType;
import com.currentbooking.utilits.cb_api.responses.BusTypeList;

import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.SSLSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingViewModel extends ViewModel {
    private final TicketBookingServices ticketBookingServices;

    public TicketBookingViewModel() {
        ticketBookingServices = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        getBusOperators();
    }
    private MutableLiveData<BusOperator> selectedBusOperator;
    private MutableLiveData<BusType> selectBusType;

    private MutableLiveData<ArrayList<BusOperator>> busOperators;
    private MutableLiveData<ArrayList<BusType>> busTypes;

    public LiveData<ArrayList<BusOperator>> getBusOperators() {
        if (busOperators == null) {
            busOperators = new MutableLiveData<>();
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
        if(null == selectedBusOperator){
            selectedBusOperator = new MutableLiveData<>();
        }
        return selectedBusOperator;
    }
    private void loadBusOperators() {
        ticketBookingServices.getBusOperators().enqueue(new Callback<BusOperatorList>() {
            @Override
            public void onResponse(Call<BusOperatorList> call, Response<BusOperatorList> response) {
                if(response.isSuccessful()) {
                    BusOperatorList data = response.body();
                    if(data.getStatus().equals("success")) {
                        if (null != data.getBusOperatorList().getBusOperators()) {
                            busOperators.setValue(data.getBusOperatorList().getBusOperators());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BusOperatorList> call, Throwable t) {

            }
        });
    }

    public void loadBusTypes() {
        String operator = selectedBusOperator.getValue().getOperatorCode().toLowerCase();
        ticketBookingServices.getBusTypes(operator).enqueue(new Callback<BusTypeList>() {
            @Override
            public void onResponse(Call<BusTypeList> call, Response<BusTypeList> response) {
                if(response.isSuccessful()) {
                    BusTypeList data = response.body();
                    if(data.getStatus().equals("success")) {
                        if (null != data.getBusTypes().getBusTypes()) {
                            busTypes.setValue(data.getBusTypes().getBusTypes());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BusTypeList> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<BusType>  getSelectedBusType() {
        if(null == selectBusType) {
            selectBusType = new MutableLiveData<>();
        }
        return selectBusType;
    }

    public void loadPickupDropPoints() {

    }
}
