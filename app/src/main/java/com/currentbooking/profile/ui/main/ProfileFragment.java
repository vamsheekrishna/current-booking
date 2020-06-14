package com.currentbooking.profile.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.Calendar;
import java.util.Objects;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    // private ProfileViewModel mViewModel;
    TextView dob;
    OnProfileListener mListener;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnProfileListener)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("My Profile");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null) {
            /*mViewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);


            mViewModel.getFirstName().setValue(MyProfile.);
            mViewModel.getLastName().getValue());
            mViewModel.getMobileNumber().getValue());
            mViewModel.getEmail().getValue());
            mViewModel.getDob().getValue()));
            mViewModel.getAddress1().getValue());
            mViewModel.getAddress2().getValue());
            mViewModel.getState().getValue());
            mViewModel.getPinCode().getValue());*/

        }//.of(this).get(ProfileViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.first_name)).setText(MyProfile.getInstance().getFirstName());
        ((TextView)view.findViewById(R.id.last_name)).setText(MyProfile.getInstance().getLastName());
        ((TextView)view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView)view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        dob = ((TextView)view.findViewById(R.id.dob));
        dob.setText(MyProfile.getInstance().getDob());
        dob.setOnClickListener(this);
        ((TextView)view.findViewById(R.id.address1)).setText(MyProfile.getInstance().getAddress1());
        ((TextView)view.findViewById(R.id.address2)).setText(MyProfile.getInstance().getAddress2());
        ((TextView)view.findViewById(R.id.state)).setText(MyProfile.getInstance().getState());
        ((TextView)view.findViewById(R.id.pin_code)).setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.edit_profile).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                mListener.goToEditProfile();
                break;
            case R.id.dob:
                break;
        }
    }
}