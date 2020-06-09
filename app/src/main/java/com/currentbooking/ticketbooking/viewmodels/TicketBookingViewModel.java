package com.currentbooking.ticketbooking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.ticketbooking.BusOperator;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;

import java.util.ArrayList;
import java.util.List;

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

    private MutableLiveData<ArrayList<BusOperator>> busOperators;

    public LiveData<ArrayList<BusOperator>> getBusOperators() {
        if (busOperators == null) {
            busOperators = new MutableLiveData<>();
            loadBusOperators();
        }
        return busOperators;
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
                        if (null != data.getBusOperatorList().getBusOperators().get(0)) {
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
}
