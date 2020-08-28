package com.currentbooking.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.currentbooking.R;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.Objects;

public class TermsFragment extends BaseFragment {

    public TermsFragment() {
        // Required empty public constructor
    }

    public static TermsFragment newInstance() {
        return new TermsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Terms and Condition");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms, container, false);
    }
}
