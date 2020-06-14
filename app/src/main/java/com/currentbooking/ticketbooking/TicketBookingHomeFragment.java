package com.currentbooking.ticketbooking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
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

public class TicketBookingHomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 101;

    // RecyclerView recyclerView;
    private String mParam1;
    private String mParam2;
    private OnTicketBookingListener mListener;
    private TextView selectTransport, pickUp, dropPoint, selectBusType;
    private TicketBookingViewModel ticketBookingModule;
    private View bus_point;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private SupportMapFragment mapFragment;

    public TicketBookingHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener)context;
    }

    public static TicketBookingHomeFragment newInstance(String param1, String param2) {
        TicketBookingHomeFragment fragment = new TicketBookingHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            if(currentLocation != null) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            } else {
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        // Inflate the select_bus_points for this fragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        return inflater.inflate(R.layout.fragment_ticket_booking_home, container, false);
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                // Toast.makeText(getContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                assert mapFragment != null;
                mapFragment.getMapAsync(callback);
            }
        });
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
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
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
            if( null == busTypes || busTypes.size()<=0) {
                bus_point.setVisibility(View.GONE);
            }
        });

        ticketBookingModule.getSelectedPickUpPoint().observe(getActivity(), busPoint -> {
            pickUp.setText(busPoint.getStopName());
        });

        ticketBookingModule.getSelectedDropPoint().observe(getActivity(), busPoint -> {
            dropPoint.setText(busPoint.getStopName());
        });

        ticketBookingModule.getSelectedBusOperator().observe(getActivity(), busOperator -> {
            if(null != busOperator) {
                selectTransport.setText(busOperator.opertorName);
                selectBusType.setVisibility(View.VISIBLE);
                bus_point.setVisibility(View.GONE);
            }
        });

        ticketBookingModule.getSelectedBusType().observe(getActivity(), busType -> {
            if(null != busType && null != busType.getBusTypeName() && busType.getBusTypeName().length()>0) {
                selectBusType.setText(busType.getBusTypeName());
                bus_point.setVisibility(View.VISIBLE);
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
                        BusStopObject pickup = ticketBookingModule.getSelectedPickUpPoint().getValue();
                        ticketBookingModule.getSelectedPickUpPoint().setValue(ticketBookingModule.getSelectedDropPoint().getValue());
                        ticketBookingModule.getSelectedDropPoint().setValue(pickup);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(rotate);
                break;
            case R.id.select_transport:
                mListener.goToOptionSelection(0);
                break;
            case R.id.select_bus_type:
                mListener.goToOptionSelection(1);
                break;
            case R.id.pick_up:
                mListener.goToBusStopSelect(2);
                break;
            case R.id.drop_point:
                mListener.goToBusStopSelect(3);
                break;
            case R.id.select_bus:
                mListener.goToSelectBus();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            }
        }
    }
}