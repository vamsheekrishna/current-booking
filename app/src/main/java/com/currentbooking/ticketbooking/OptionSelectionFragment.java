package com.currentbooking.ticketbooking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.databinding.FragmentOptionSelectionBinding;
import com.currentbooking.ticketbooking.adapters.OptionSelectionAdapter;
import com.currentbooking.ticketbooking.viewmodels.ItemData;
import com.currentbooking.ticketbooking.viewmodels.OptionSelectionViewModel;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MvvmView;
import com.currentbooking.utilits.MyViewModelFactory;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;
import com.currentbooking.utilits.cb_api.responses.BusType;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class OptionSelectionFragment extends BaseFragment implements View.OnClickListener, MvvmView.View {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mIndex;
    private String mParam2;
    // private CallBackInterface callBackInterface;
    private TicketBookingViewModel ticketBookingModule;
    private ArrayList<BusOperator> busOperator;
    private ArrayList<BusType> busTypes;
    private ArrayList<BusStopObject> busPoints;
    private OnTicketBookingListener mListener;

    public static OptionSelectionFragment newInstance(int index, String param2) {
        OptionSelectionFragment fragment = new OptionSelectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private OptionSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initDataBinding(inflater);
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(mParam2);
    }
    private View initDataBinding(@NotNull LayoutInflater inflater) {
        FragmentOptionSelectionBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_option_selection,
                null, false);
        OptionSelectionViewModel optionSelectionViewModel = new OptionSelectionViewModel();
        dataBinding.setViewModel(optionSelectionViewModel);
        //ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);
        ticketBookingModule = new ViewModelProvider(Objects.requireNonNull(getActivity()), new MyViewModelFactory(this)).get(TicketBookingViewModel.class);
        loadUIComponents(dataBinding);
        return dataBinding.getRoot();
    }

    private void loadUIComponents(FragmentOptionSelectionBinding dataBinding) {
        SearchView searchView = dataBinding.searchView;
        RecyclerView resultsListField = dataBinding.searchResultsField;
        resultsListField.setHasFixedSize(false);
        /*DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider)));
        resultsListField.addItemDecoration(divider);*/
        OptionSelectionAdapter optionSelectionAdapter = new OptionSelectionAdapter(requireActivity(), getCitiesList(), this);
        resultsListField.setAdapter(optionSelectionAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() != 0) {
                    optionSelectionAdapter.getFilter().filter(newText);
                } else {
                    optionSelectionAdapter.updateItems(getCitiesList());
                    optionSelectionAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    private ArrayList<ItemData> getCitiesList() {

        ArrayList<ItemData> list = new ArrayList<>();
        if (mIndex == 0) {
            requireActivity().setTitle(getString(R.string.select_bus_service));
            if (ticketBookingModule.getBusOperators() != null) {
                busOperator = ticketBookingModule.getBusOperators().getValue();
                if (busOperator != null && !busOperator.isEmpty()) {
                    for (int i = 0; i < busOperator.size(); i++) {
                        list.add(new ItemData(busOperator.get(i).getOpertorName(), i));
                    }
                }
            }
        } else if (mIndex == 1) {
            requireActivity().setTitle(getString(R.string.select_bus_type));
            if (ticketBookingModule.getBusTypes() != null) {
                busTypes = ticketBookingModule.getBusTypes().getValue();
                if (busTypes != null && !busTypes.isEmpty()) {
                    for (int i = 0; i < busTypes.size(); i++) {
                        list.add(new ItemData(busTypes.get(i).getBusTypeName(), i));
                    }
                }
            }
        } /*else if (mIndex == 2) {
            requireActivity().setTitle(getString(R.string.selected_pickup_point));
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
        }*//* else if (mIndex == 3) {
            requireActivity().setTitle(getString(R.string.select_drop_point));
            if (ticketBookingModule.getBusPoints() != null) {
                busPoints = ticketBookingModule.getBusPoints().getValue();
                if (busPoints != null && !busPoints.isEmpty()) {
                    for (int i = 0; i < busPoints.size(); i++) {
                        if (!Objects.requireNonNull(ticketBookingModule.getSelectedPickUpPoint().getValue()).getName().equals(busPoints.get(i).getName())) {
                            list.add(new ItemData(busPoints.get(i).getName(), i));
                        }
                    }
                }
            }
        }*/
        //Collections.sort(list);
        return list;
    }

    @Override
    public void onClick(View v) {
        int Item = (int) v.getTag();
        if (mIndex == 0) {
            BusOperator selectedOperator = busOperator.get(Item);
            if (null == ticketBookingModule.getSelectedBusOperator().getValue() || !Objects.requireNonNull(ticketBookingModule.getSelectedBusOperator().getValue()).getOpertorName().equals(selectedOperator.getOpertorName())) {
                ticketBookingModule.getSelectedBusOperator().setValue(selectedOperator);
                ticketBookingModule.onBusOperatorChanged();
            }
        } else if (mIndex == 1) {
            BusType selectedBusType = busTypes.get(Item);
            if (null == ticketBookingModule.getSelectedBusType().getValue().getBusTypeID() || !ticketBookingModule.getSelectedBusType().getValue().getBusTypeID().equals(selectedBusType.getBusTypeID())) {
                ticketBookingModule.getSelectedBusType().setValue(selectedBusType);
            }
        }
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.showBadge(true);
        mListener.showHamburgerIcon(true);
    }
}