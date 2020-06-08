package com.currentbooking.ticketbooking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingViewModel extends ViewModel {
    private final TicketBookingServices ticketBookingServices;
    TicketBookingViewModel() {
        ticketBookingServices = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
    }
    private MutableLiveData<List<BusOperator>> busOperators;
    private MutableLiveData<BusOperator> selectedBusOperator;
    public LiveData<List<BusOperator>> getBusOperators() {
        if (busOperators == null) {
            busOperators = new MutableLiveData<List<BusOperator>>();
            loadBusOperators();
        }
        return busOperators;
    }

    private void loadBusOperators() {
        ticketBookingServices.getBusOperators().enqueue(new Callback<BusOperatorList>() {
            @Override
            public void onResponse(Call<BusOperatorList> call, Response<BusOperatorList> response) {

            }

            @Override
            public void onFailure(Call<BusOperatorList> call, Throwable t) {

            }
        });
    }
}
