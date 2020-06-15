package com.currentbooking.ticketbooking;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.adapters.BusStopAdapter;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.cb_api.responses.BusStopResponse;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusPointFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TicketBookingViewModel ticketBookingModule;
    private TicketBookingServices ticketService;
    BusStopAdapter busStopAdapter;
    private int mIndex;

    public BusPointFragment() {
        // Required empty public constructor
    }

    public static BusPointFragment newInstance(int param1, String param2) {
        BusPointFragment fragment = new BusPointFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bus_point_adapter, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Select Bus Stop");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
        SearchView searchView = view.findViewById(R.id.searchView);
        RecyclerView resultsListField = view.findViewById(R.id.search_results_field);
        resultsListField.setHasFixedSize(false);
        /*DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider)));
        resultsListField.addItemDecoration(divider);*/
        busStopAdapter = new BusStopAdapter(requireActivity(), new ArrayList<>(), this);
        resultsListField.setAdapter(busStopAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "onQueryTextSubmit "+query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 3) {
                    busStopAdapter.updateItems(new ArrayList<>());
                    busStopAdapter.notifyDataSetChanged();
                } else if (newText.length() == 3) {
                    getBusStopList(newText);
                } else {
                    busStopAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    private void getBusStopList(String stopPrefix) {
        progressDialog.show();
        ticketService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        ticketService.getBusStopList("MSRTC", stopPrefix).enqueue(new Callback<BusStopResponse>() {
            @Override
            public void onResponse(Call<BusStopResponse> call, Response<BusStopResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getStatus().equalsIgnoreCase(getString(R.string.success))) {
                        ArrayList<BusStopObject> data = response.body().getBusStopList().getBusStopList();
                        /*for (int i = 0; i < data.size(); i++) {
                            if (!Objects.requireNonNull(ticketBookingModule.getSelectedDropPoint().getValue()).getStopName().equals(data.get(i).getStopName())) {
                                list.add(new ItemData(data.get(i).getStopName(), i));
                            }
                        }*/
                        busStopAdapter.updateItems(data);
                        busStopAdapter.notifyDataSetChanged();
                    } else {
                        String data = response.body().getMsg();
                        showDialog("", data);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BusStopResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        /*
        if (ticketBookingModule.getBusPoints() != null) {
            busPoints = ticketBookingModule.getBusPoints().getValue();
            if (busPoints != null && !busPoints.isEmpty()) {
                for (int i = 0; i < busPoints.size(); i++) {
                    if (!Objects.requireNonNull(ticketBookingModule.getSelectedDropPoint().getValue()).getName().equals(busPoints.get(i).getName())) {
                        list.add(new ItemData(busPoints.get(i).getName(), i));
                    }
                }
            }
        }
        */
    }

    @Override
    public void onClick(View v) {
        BusStopObject data = (BusStopObject)v.getTag();
        if (mIndex == 2) {
            // BusStopObject selectedBusPoint = busPoints.get(index);
            if (!Objects.requireNonNull(ticketBookingModule.getSelectedPickUpPoint().getValue()).getStopName().equals(data.getStopName()) || !ticketBookingModule.getSelectedDropPoint().getValue().getStopName().equals(data.getStopName())) {
                ticketBookingModule.getSelectedPickUpPoint().setValue(data);
            }
        } else if (mIndex == 3) {
            // BusStopObject selectedBusPoint = busPoints.get(index);
            if (!Objects.requireNonNull(ticketBookingModule.getSelectedDropPoint().getValue()).getStopName().equals(data.getStopName()) || !ticketBookingModule.getSelectedPickUpPoint().getValue().getStopName().equals(data.getStopName())) {
                ticketBookingModule.getSelectedDropPoint().setValue(data);
            }
        }
        hideSoftKeyboard();
        Objects.requireNonNull(getActivity()).onBackPressed();
        // Toast.makeText(getActivity(), "Clicked. ", Toast.LENGTH_LONG).show();
    }
    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {

        }
    }
}