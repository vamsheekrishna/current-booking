package com.currentbooking.ticketbooking;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.MyLocation;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.MyViewModelFactory;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class TicketBookingHomeFragment extends BaseFragment implements View.OnClickListener, MvvmView.View {

    // RecyclerView recyclerView;
    private OnTicketBookingListener mListener;
    private TextView selectTransport, pickUp, dropPoint, selectBusType;
    private TicketBookingViewModel ticketBookingModule;
    private View bus_point;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private SupportMapFragment mapFragment;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;

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
    }

    public static TicketBookingHomeFragment newInstance(String param1, String param2) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
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
        //ticketBookingModule = new ViewModelProvider(requireActivity(), getDefaultViewModelProviderFactory()).get(TicketBookingViewModel.class);
        //ticketBookingModule = ViewModelProviders.of(this, new MyViewModelFactory(this)).get(TicketBookingViewModel.class);
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity()), new MyViewModelFactory(this)).get(TicketBookingViewModel.class);
        view.findViewById(R.id.swipe_points).setOnClickListener(this);

        selectTransport = view.findViewById(R.id.select_transport);
        selectTransport.setOnClickListener(this);

        selectBusType = view.findViewById(R.id.select_bus_type);
        selectBusType.setOnClickListener(this);
        selectBusType.setVisibility(View.GONE);
        bus_point = view.findViewById(R.id.bus_point);
        bus_point.setVisibility(View.GONE);

        pickUp = view.findViewById(R.id.pick_up);
        pickUp.setOnClickListener(this);

        dropPoint = view.findViewById(R.id.drop_point);
        dropPoint.setOnClickListener(this);
        view.findViewById(R.id.select_bus).setOnClickListener(this);
        ticketBookingModule.getBusTypes().observe(getActivity(), busTypes -> {
            /*if( null == busTypes || busTypes.isEmpty()) {
                bus_point.setVisibility(View.GONE);
            }*/
        });

        ticketBookingModule.getSelectedPickUpPoint().observe(getActivity(), busPoint -> {
            pickUp.setText(busPoint.getStopName());
        });

        ticketBookingModule.getSelectedDropPoint().observe(getActivity(),          busPoint -> {
            String stopName = busPoint.getStopName();
            if(!TextUtils.isEmpty(stopName)) {
                dropPoint.setText(stopName);
                selectBusType.setVisibility(View.VISIBLE);
            }
        });

        ticketBookingModule.getSelectedBusOperator().observe(getActivity(), busOperator -> {
            if(null != busOperator) {
                selectTransport.setText(busOperator.getOpertorName());
                bus_point.setVisibility(View.VISIBLE);
            }
        });

        ticketBookingModule.getSelectedBusType().observe(getActivity(), busType -> {
            if(null != busType && null != busType.getBusTypeName() && busType.getBusTypeName().length()>0) {
                selectBusType.setText(busType.getBusTypeName());
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
                        //ticketBookingModule.getSelectedPickUpPoint().setValue(ticketBookingModule.getSelectedDropPoint().getValue());
                        ticketBookingModule.getSelectedDropPoint().setValue(pickupPointDetails);
                        ticketBookingModule.getSelectedPickUpPoint().setValue(dropPointDetails);

                        if(dropPointDetails != null) {
                            pickUp.setText(dropPointDetails.getStopName());
                        } else {
                            pickUp.setText("");
                        }

                        if(pickupPointDetails != null) {
                            dropPoint.setText(pickupPointDetails.getStopName());
                        } else {
                            dropPoint.setText("");
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(rotate);
                break;
            case R.id.select_transport:
                mListener.gotoOptionSelection(0, getString(R.string.select_transport));
                break;
            case R.id.select_bus_type:
                mListener.gotoOptionSelection(1, getString(R.string.select_bus_type));
                break;
            case R.id.pick_up:
                mListener.gotoBusStopSelect(2, latitude, longitude);
                break;
            case R.id.drop_point:
                mListener.gotoBusStopSelect(3, latitude, longitude);
                break;
            case R.id.select_bus:
                if(ticketBookingModule.getSelectedPickUpPoint().getValue() != null && ticketBookingModule.getSelectedPickUpPoint().getValue().getStopCode().length()>1 &&
                        Objects.requireNonNull(ticketBookingModule.getSelectedDropPoint().getValue()).getStopCode()!= null && ticketBookingModule.getSelectedDropPoint().getValue().getStopCode().length()>1) {
                    String pickupPointStopCode = ticketBookingModule.getSelectedPickUpPoint().getValue().getStopCode();
                    String dropPointStopCode = ticketBookingModule.getSelectedDropPoint().getValue().getStopCode();
                    if(!pickupPointStopCode.equalsIgnoreCase(dropPointStopCode)) {
                        mListener.gotoSelectBus(selectTransport.getText().toString(), selectBusType.getText().toString());
                    } else {
                        showDialog("", "Pickup Point and Drop Point should not be Same.");
                    }
                } else {
                    showDialog("", "Please enter your travel details.");
                }
        }
    }
}