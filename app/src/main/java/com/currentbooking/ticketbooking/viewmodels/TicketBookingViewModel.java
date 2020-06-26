package com.currentbooking.ticketbooking.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusOperatorList;
import com.currentbooking.utilits.cb_api.responses.BusPoint;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.cb_api.responses.BusType;
import com.currentbooking.utilits.cb_api.responses.BusTypeList;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.cb_api.responses.ConcessionListResponse;
import com.currentbooking.utilits.cb_api.responses.ConcessionRates;
import com.currentbooking.utilits.cb_api.responses.ConcessionRatesListResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingViewModel extends ViewModel {
    private final TicketBookingServices ticketBookingServices;

    private MutableLiveData<BusOperator> selectedBusOperator;
    private MutableLiveData<ArrayList<BusOperator>> busOperators;

    private MutableLiveData<ArrayList<BusType>> busTypes;
    private MutableLiveData<BusType> selectBusType;

    private MutableLiveData<ArrayList<BusPoint>> busPoints = new MutableLiveData<>();

    private MutableLiveData<BusStopObject> selectedPickUpPoint = new MutableLiveData<>();
    private MutableLiveData<BusStopObject> selectedDropPoint = new MutableLiveData<>();

    private MutableLiveData<BusObject> selectedBusObject = new MutableLiveData<>();

    private MutableLiveData<Concession> selectedConcession = new MutableLiveData<>();
    private MutableLiveData<ConcessionRates> selectedConcessionRate = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Concession>> concessionList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ConcessionRates>> concessionRates = new MutableLiveData<>();
    private MutableLiveData<Bitmap> userProfileImage = new MutableLiveData<>();

    public TicketBookingViewModel() {
        ticketBookingServices = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
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

    public MutableLiveData<Bitmap> getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Bitmap userImageBitmap) {
        userProfileImage.setValue(userImageBitmap);
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
        selectedPickUpPoint.setValue(new BusStopObject());
        selectedDropPoint.setValue(new BusStopObject());
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
    public MutableLiveData<BusObject> getSelectedBusObject() {
        return selectedBusObject;
    }
    private void loadBusOperators() {
        ticketBookingServices.getBusOperators().enqueue(new Callback<BusOperatorList>() {
            @Override
            public void onResponse(Call<BusOperatorList> call, Response<BusOperatorList> response) {
                if(response.isSuccessful()) {
                    LoggerInfo.printLog("operators list response", response.body());
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
        getConcession(Objects.requireNonNull(selectedBusOperator.getValue()).getOperatorCode());
        getConcessionRate();
        loadBusTypes();
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
            public void onResponse(Call<ConcessionListResponse> call, Response<ConcessionListResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        ArrayList<Concession> data = response.body().getConcessionList().getConcessions();
                        concessionList.setValue(data);
                    } else {
                        ArrayList<Concession> data = new ArrayList<>();
                        concessionList.setValue(data);
                    }
                    selectedConcession.setValue(new Concession());
                }
            }

            @Override
            public void onFailure(Call<ConcessionListResponse> call, Throwable t) {
                ArrayList<Concession> data = new ArrayList<>();
                concessionList.setValue(data);
                selectedConcession.setValue(new Concession());
            }
        });
    }
    private void getConcessionRate() {
        ticketBookingServices.getConcessionRatesList(selectedBusOperator.getValue().getOperatorCode()).enqueue(new Callback<ConcessionRatesListResponse>() {
            @Override
            public void onResponse(Call<ConcessionRatesListResponse> call, Response<ConcessionRatesListResponse> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase("success")) {
                    ArrayList<ConcessionRates> data = response.body().getConcessionRatesList().getConcessionRates();
                    concessionRates.setValue(data);
                    selectedConcessionRate.setValue(new ConcessionRates());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ConcessionRatesListResponse> call, Throwable t) {

            }
        });
    }
    public void onConcessionSelected(Concession concession) {
        selectedConcession.setValue(concession);
    }
    public void onConcessionRateSelected(ConcessionRates concessionRates) {
        selectedConcessionRate.setValue(concessionRates);
    }
    public void onBusTypeSelected() {
        resetBusType();
        resetBusPointData();
        // loadBusPoints();
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
