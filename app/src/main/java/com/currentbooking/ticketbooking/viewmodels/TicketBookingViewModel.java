package com.currentbooking.ticketbooking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;
import com.currentbooking.utilits.cb_api.responses.BusPoint;
import com.currentbooking.utilits.cb_api.responses.BusStopList;
import com.currentbooking.utilits.cb_api.responses.BusType;
import com.currentbooking.utilits.cb_api.responses.BusTypeList;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.SSLSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingViewModel extends ViewModel {
    private final TicketBookingServices ticketBookingServices;

    private MutableLiveData<BusOperator> selectedBusOperator;
    private MutableLiveData<BusType> selectBusType;

    private MutableLiveData<ArrayList<BusOperator>> busOperators;
    private MutableLiveData<ArrayList<BusType>> busTypes;

    private MutableLiveData<ArrayList<BusPoint>> busPoints = new MutableLiveData<>();

    private MutableLiveData<BusPoint> selectedPickUpPoint = new MutableLiveData<>();
    private MutableLiveData<BusPoint> selectedDropPoint = new MutableLiveData<>();

    public TicketBookingViewModel() {
        ticketBookingServices = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        getBusOperators();
    }

    public LiveData<ArrayList<BusPoint>> getBusPoints() {
        return busPoints;
    }
    public MutableLiveData<BusPoint> getSelectedPickUpPoint() {
        return selectedPickUpPoint;
    }
    public MutableLiveData<BusPoint> getSelectedDropPoint() {
        return selectedDropPoint;
    }

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

    public void loadBusPoints() {
        ArrayList<BusPoint> list = new ArrayList<>();
        list.add(new BusPoint("Adoni", 0));
        list.add(new BusPoint("Amaravati", 1));
        list.add(new BusPoint("Anantapur", 2));
        list.add(new BusPoint("Chandragiri", 3));
        list.add(new BusPoint("Chittoor", 4));
        list.add(new BusPoint("Dowlaiswaram", 5));
        list.add(new BusPoint("Eluru", 6));
        list.add(new BusPoint("Guntur", 7));
        list.add(new BusPoint("Kadapa", 8));
        list.add(new BusPoint("Kakinada", 9));
        list.add(new BusPoint("Kurnool", 10));
        list.add(new BusPoint("Machilipatnam", 11));
        list.add(new BusPoint("Nagarjunakoṇḍa", 12));
        list.add(new BusPoint("Rajahmundry", 13));
        list.add(new BusPoint("Srikakulam", 14));
        list.add(new BusPoint("Tirupati", 15));
        list.add(new BusPoint("Vijayawada", 16));
        list.add(new BusPoint("Visakhapatnam", 17));
        list.add(new BusPoint("Vizianagaram", 18));
        list.add(new BusPoint("Hyderabad", 19));
        list.add(new BusPoint("Karimnagar", 20));
        list.add(new BusPoint("Khammam", 21));
        list.add(new BusPoint("Mahbubnagar", 22));
        list.add(new BusPoint("Nizamabad", 23));
        list.add(new BusPoint("Sangareddi", 24));
        list.add(new BusPoint("Warangal", 25));
        busPoints.setValue(list);
        selectedPickUpPoint.setValue(new BusPoint("", -1));
        selectedDropPoint.setValue(new BusPoint("", -1));
        /*ticketBookingServices.getBusPoints(Objects.requireNonNull(selectedBusOperator.getValue()).opertorName,
                selectBusType.getValue().getBusTypeID()).enqueue(new Callback<BusStopList>() {
            @Override
            public void onResponse(Call<BusStopList> call, Response<BusStopList> response) {

            }

            @Override
            public void onFailure(Call<BusStopList> call, Throwable t) {

            }
        });*/


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
                    } else {
                        busOperators.setValue(new ArrayList<>());
                    }
                }
            }

            @Override
            public void onFailure(Call<BusOperatorList> call, Throwable t) {
                busOperators.setValue(new ArrayList<>());
            }
        });
    }

    public void loadBusTypes() {
        if(selectedBusOperator!=null) {
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
                        } else {
                            busTypes.setValue(new ArrayList<>());
                        }
                    }
                }

                @Override
                public void onFailure(Call<BusTypeList> call, Throwable t) {
                    busTypes.setValue(new ArrayList<>());
                }
            });
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
        loadBusTypes();
    }

    public void onBusTypeSelected() {
        resetBusType();
        resetBusPointData();
        loadBusPoints();
    }
    public void resetBusType() {
        busTypes.setValue(new ArrayList<>());
        selectBusType.setValue(new BusType());
    }
    public void resetBusPointData() {
        selectedPickUpPoint.setValue(new BusPoint("", -1));
        selectedDropPoint.setValue(new BusPoint("", -1));
        busPoints.setValue(new ArrayList<>());
    }
}
