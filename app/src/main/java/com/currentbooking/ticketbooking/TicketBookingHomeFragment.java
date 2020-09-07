package com.currentbooking.ticketbooking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.adapters.CustomSpinnerAdapter;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.MyLocation;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.MyViewModelFactory;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.cb_api.responses.BusType;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.wallet.MyWalletBalance;
import com.currentbooking.wallet.WalletBalance;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingHomeFragment extends BaseFragment implements View.OnClickListener, MvvmView.View {

    // RecyclerView recyclerView;
    private OnTicketBookingListener mListener;
    private TextView pickUp, dropPoint, balanceField;
    private TicketBookingViewModel ticketBookingModule;
    private View bus_point;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private SupportMapFragment mapFragment;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;
    private BusOperator selectedOperatorDetails;
    private BusType selectedBusTypeDetails;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public TicketBookingHomeFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public Context getContext() {
        return super.getActivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
        sharedpreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);

    }

    public static TicketBookingHomeFragment newInstance() {
        return new TicketBookingHomeFragment();
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (currentLocation != null) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.ticket_booking));
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            myProfile.getCurrentBookingTicketCount().observe(Objects.requireNonNull(getActivity()), integer -> mListener.updateBadgeCount());
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        return inflater.inflate(R.layout.fragment_ticket_booking_home, container, false);
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
                // Toast.makeText(getContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);
                }
            } else {
                locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    onGPS();
                } else {
                    getLocation();
                }
            }
        });
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton("No", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                currentLocation = locationGPS;
                latitude = locationGPS.getLatitude();
                longitude = locationGPS.getLongitude();
                updateMap();
            } else {
                MyLocation myLocation = new MyLocation(requireActivity());
                myLocation.getLocation(locationResult);
            }
        }
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(Location location) {
            if (location != null) {
                currentLocation = location;
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                updateMap();
            }
        }
    };

    private void updateMap() {
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            fetchLocation();
        }
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity()), new MyViewModelFactory(this)).get(TicketBookingViewModel.class);
        view.findViewById(R.id.swipe_points).setOnClickListener(this);

        Spinner selectTransportSpinnerField = view.findViewById(R.id.select_transport_spinner);
        Spinner selectBusTypeSpinnerField = view.findViewById(R.id.select_bus_type_spinner);

        RelativeLayout selectBusTypeLayoutField = view.findViewById(R.id.select_bus_type_layout_field);
        selectBusTypeLayoutField.setVisibility(View.GONE);
        bus_point = view.findViewById(R.id.bus_point);
        bus_point.setVisibility(View.GONE);

        pickUp = view.findViewById(R.id.pick_up);
        view.findViewById(R.id.pick_up_layout_field).setOnClickListener(this);
        view.findViewById(R.id.drop_point_layout_field).setOnClickListener(this);

        dropPoint = view.findViewById(R.id.drop_point);
        balanceField = view.findViewById(R.id.balance);
        view.findViewById(R.id.select_bus).setOnClickListener(this);
        ticketBookingModule.getBusTypes().observe(getActivity(), busTypes -> {

        });

        ticketBookingModule.getSelectedPickUpPoint().observe(getActivity(), busPoint -> {
            String stopName = busPoint.getStopName();
            if (!TextUtils.isEmpty(stopName)) {
                pickUp.setText(stopName);
            }

            BusStopObject pickupPointDetails = ticketBookingModule.getSelectedPickUpPoint().getValue();
            BusStopObject dropPointDetails = ticketBookingModule.getSelectedDropPoint().getValue();
            if (pickupPointDetails != null && dropPointDetails != null) {
                String pickupStopCode = pickupPointDetails.getStopCode();
                String dropPointStopCode = dropPointDetails.getStopCode();
                if (!TextUtils.isEmpty(pickupStopCode) && !TextUtils.isEmpty(dropPointStopCode)) {
                    selectBusTypeLayoutField.setVisibility(View.VISIBLE);
                } else {
                    selectBusTypeLayoutField.setVisibility(View.GONE);
                }
            } else {
                selectBusTypeLayoutField.setVisibility(View.GONE);
            }
        });

        ticketBookingModule.getSelectedDropPoint().observe(getActivity(), busPoint -> {
            String stopName = busPoint.getStopName();
            if (!TextUtils.isEmpty(stopName)) {
                dropPoint.setText(stopName);
            }
            BusStopObject pickupPointDetails = ticketBookingModule.getSelectedPickUpPoint().getValue();
            BusStopObject dropPointDetails = ticketBookingModule.getSelectedDropPoint().getValue();
            if (pickupPointDetails != null && dropPointDetails != null) {
                String pickupStopCode = pickupPointDetails.getStopCode();
                String dropPointStopCode = dropPointDetails.getStopCode();
                if (!TextUtils.isEmpty(pickupStopCode) && !TextUtils.isEmpty(dropPointStopCode)) {
                    selectBusTypeLayoutField.setVisibility(View.VISIBLE);
                } else {
                    selectBusTypeLayoutField.setVisibility(View.GONE);
                }
            } else {
                selectBusTypeLayoutField.setVisibility(View.GONE);
            }
        });

        ticketBookingModule.getBusOperators().observe(requireActivity(), busOperators -> {
            if (busOperators != null && !busOperators.isEmpty()) {
                List<Object> busOperatorList = new ArrayList<>(busOperators);
                CustomSpinnerAdapter busOperatorsSpinnerAdapter = new CustomSpinnerAdapter(requireActivity(), busOperatorList);
                selectTransportSpinnerField.setAdapter(busOperatorsSpinnerAdapter);
            }
        });


        selectTransportSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object lSelectedObject = selectTransportSpinnerField.getSelectedItem();
                selectedOperatorDetails = null;
                bus_point.setVisibility(View.GONE);
                if (lSelectedObject instanceof BusOperator) {
                    selectedOperatorDetails = (BusOperator) lSelectedObject;
                    bus_point.setVisibility(View.VISIBLE);
                    ticketBookingModule.getSelectedBusOperator().setValue(selectedOperatorDetails);
                    ticketBookingModule.onBusOperatorChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticketBookingModule.getBusTypes().observe(getActivity(), busTypes -> {
            if (busTypes != null && !busTypes.isEmpty()) {
                List<Object> busTypesList = new ArrayList<>();
                busTypesList.add(getString(R.string.select_bus_type));
                busTypesList.addAll(busTypes);
                CustomSpinnerAdapter busTypesSpinnerAdapter = new CustomSpinnerAdapter(requireActivity(), busTypesList);
                selectBusTypeSpinnerField.setAdapter(busTypesSpinnerAdapter);
            }
        });

        selectBusTypeSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object lSelectedObject = selectBusTypeSpinnerField.getSelectedItem();
                selectedBusTypeDetails = null;
                if (lSelectedObject instanceof BusType) {
                    selectedBusTypeDetails = (BusType) lSelectedObject;
                    ticketBookingModule.getSelectedBusType().setValue(selectedBusTypeDetails);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticketBookingModule.getSelectedBusType().observe(getActivity(), busType -> {
            if (null != busType && null != busType.getBusTypeName() && busType.getBusTypeName().length() > 0) {
                //selectBusType.setText(busType.getBusTypeName());
            }
        });

        TicketBookingServices ticketBookingService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        ticketBookingService.getWalletBalance(MyProfile.getInstance().getUserId(),"").enqueue(new Callback<WalletBalance>() {
            @Override
            public void onResponse(@NotNull Call<WalletBalance> call, @NotNull Response<WalletBalance> response) {
                try {
                    if (response.isSuccessful()) {
                        WalletBalance data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                WalletBalance responseDetails = response.body();
                                if (responseDetails != null) {
                                    MyWalletBalance walletBalanceDetails = responseDetails.getAvailableBalance();
                                    String walletBalanceText = String.format("%s : %s", getString(R.string.my_wallet_balance), walletBalanceDetails.getwallet_balance());
                                    balanceField.setText(walletBalanceText);
                                    editor = sharedpreferences.edit();
                                    editor.putString("balance", walletBalanceDetails.getwallet_balance());
                                    editor.apply();
                                }
                            }
                        }
                    }
                } finally {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<WalletBalance> call, @NotNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipe_points:
                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setFillAfter(true);
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        BusStopObject pickupPointDetails = ticketBookingModule.getSelectedPickUpPoint().getValue();
                        BusStopObject dropPointDetails = ticketBookingModule.getSelectedDropPoint().getValue();
                        BusStopObject tempDetails = pickupPointDetails;
                        pickupPointDetails = dropPointDetails;
                        dropPointDetails = tempDetails;

                        ticketBookingModule.getSelectedPickUpPoint().setValue(pickupPointDetails);
                        ticketBookingModule.getSelectedDropPoint().setValue(dropPointDetails);
                        if(dropPointDetails != null && pickupPointDetails != null) {
                            String dropPointStopCode = dropPointDetails.getStopCode();
                            if(!TextUtils.isEmpty(dropPointStopCode)) {
                                dropPoint.setText(dropPointDetails.getStopName());
                            } else {
                                dropPoint.setText("");
                            }

                            String pickUpPointStopCode = pickupPointDetails.getStopCode();
                            if(!TextUtils.isEmpty(pickUpPointStopCode)) {
                                pickUp.setText(pickupPointDetails.getStopName());
                            } else {
                                pickUp.setText("");
                            }
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(rotate);
                break;
          /*  case R.id.select_transport:
                mListener.gotoOptionSelection(0, getString(R.string.select_transport));
                break;*/
            /*case R.id.select_bus_type:
                mListener.gotoOptionSelection(1, getString(R.string.select_bus_type));
                break;*/
            case R.id.pick_up_layout_field:
                mListener.gotoBusStopSelect(2, latitude, longitude);
                break;
            case R.id.drop_point_layout_field:
                mListener.gotoBusStopSelect(3, latitude, longitude);
                break;
            case R.id.select_bus:
                if (NetworkUtility.isNetworkConnected(requireActivity())) {
                    if (ticketBookingModule.getSelectedPickUpPoint().getValue() != null && ticketBookingModule.getSelectedPickUpPoint().getValue().getStopCode().length() > 1 &&
                            Objects.requireNonNull(ticketBookingModule.getSelectedDropPoint().getValue()).getStopCode() != null && ticketBookingModule.getSelectedDropPoint().getValue().getStopCode().length() > 1) {
                        String pickupPointStopCode = ticketBookingModule.getSelectedPickUpPoint().getValue().getStopCode();
                        String dropPointStopCode = ticketBookingModule.getSelectedDropPoint().getValue().getStopCode();
                        String busTypeCd = "";
                        if (selectedBusTypeDetails != null) {
                            busTypeCd = selectedBusTypeDetails.getBusTypeCD();
                        }
                        if (TextUtils.isEmpty(busTypeCd)) {
                            busTypeCd = "";
                        }
                        if (!pickupPointStopCode.equalsIgnoreCase(dropPointStopCode)) {
                            mListener.gotoSelectBus(selectedOperatorDetails.getOpertorName(), busTypeCd);
                        } else {
                            showDialog("", "Pickup Point and Drop Point should not be Same.");
                        }
                    } else {
                        showDialog("", "Please enter your travel details.");
                    }
                } else {
                    showDialog(getString(R.string.internet_fail));
                }
                break;
            default:
                break;
        }
    }
}