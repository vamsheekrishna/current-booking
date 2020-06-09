package com.currentbooking.ticketbooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.databinding.FragmentSelectBusBinding;
import com.currentbooking.ticketbooking.adapters.SelectBusesAdapter;
import com.currentbooking.ticketbooking.viewmodels.SelectBusesViewModel;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SelectBusFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectBusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectBusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectBusFragment newInstance(String param1, String param2) {
        SelectBusFragment fragment = new SelectBusFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initDataBinding(inflater);
    }

    private View initDataBinding(@NotNull LayoutInflater inflater) {
        FragmentSelectBusBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_bus,
                null, false);
        SelectBusesViewModel selectBusesViewModel = new SelectBusesViewModel();
        dataBinding.setViewModel(selectBusesViewModel);

        loadUIComponents(dataBinding);
        return dataBinding.getRoot();
    }

    private void loadUIComponents(FragmentSelectBusBinding dataBinding) {
        RecyclerView busesResultListField = dataBinding.busesResultsListField;

        busesResultListField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                R.drawable.recycler_decoration_divider)));
        busesResultListField.addItemDecoration(divider);

        List<Object> busesList = new ArrayList<>();
        busesList.add(1);
        busesList.add(2);
        busesList.add(3);
        busesList.add(4);
        busesList.add(5);

        SelectBusesAdapter selectBusesAdapter = new SelectBusesAdapter(getActivity(), busesList);
        busesResultListField.setAdapter(selectBusesAdapter);
    }
}