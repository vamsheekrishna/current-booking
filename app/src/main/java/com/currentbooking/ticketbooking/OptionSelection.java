package com.currentbooking.ticketbooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.databinding.FragmentOptionSelectionBinding;
import com.currentbooking.ticketbooking.adapters.OptionSelectionAdapter;
import com.currentbooking.ticketbooking.viewmodels.OptionSelectionViewModel;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OptionSelection extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OptionSelection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionSelection.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionSelection newInstance(String param1, String param2) {
        OptionSelection fragment = new OptionSelection();
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        //return inflater.inflate(R.layout.fragment_option_selection, container, false);
        return initDataBinding(inflater);
    }

    private View initDataBinding(@NotNull LayoutInflater inflater) {
        FragmentOptionSelectionBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_option_selection,
                null, false);
        OptionSelectionViewModel optionSelectionViewModel = new OptionSelectionViewModel();
        dataBinding.setViewModel(optionSelectionViewModel);

        loadUIComponents(dataBinding);
        return dataBinding.getRoot();
    }

    private void loadUIComponents(FragmentOptionSelectionBinding dataBinding) {
        SearchView searchView = dataBinding.searchView;
        RecyclerView resultsListField = dataBinding.searchResultsField;

        resultsListField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                R.drawable.recycler_decoration_divider)));
        resultsListField.addItemDecoration(divider);

        OptionSelectionAdapter optionSelectionAdapter = new OptionSelectionAdapter(getActivity(), getCitiesList());
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

    private List<String> getCitiesList() {
        List<String> citiesList = new ArrayList<>();
        citiesList.add("Adoni");
        citiesList.add("Amaravati");
        citiesList.add("Anantapur");
        citiesList.add("Chandragiri");
        citiesList.add("Chittoor");
        citiesList.add("Dowlaiswaram");
        citiesList.add("Eluru");
        citiesList.add("Guntur");
        citiesList.add("Kadapa");
        citiesList.add("Kakinada");
        citiesList.add("Kurnool");
        citiesList.add("Machilipatnam");
        citiesList.add("Nagarjunakoṇḍa");
        citiesList.add("Rajahmundry");
        citiesList.add("Srikakulam");
        citiesList.add("Tirupati");
        citiesList.add("Vijayawada");
        citiesList.add("Visakhapatnam");
        citiesList.add("Vizianagaram");
        citiesList.add("Hyderabad");
        citiesList.add("Karimnagar");
        citiesList.add("Khammam");
        citiesList.add("Mahbubnagar");
        citiesList.add("Nizamabad");
        citiesList.add("Sangareddi");
        citiesList.add("Warangal");

        Collections.sort(citiesList);
        return citiesList;
    }
}