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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.currentbooking.R;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.Calendar;
import java.util.Objects;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    // private ProfileViewModel mViewModel;
    OnProfileListener mListener;
    private AppCompatEditText firstName, lastName, address1, address2, state, pinCode;
    AppCompatTextView dob;
    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
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
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
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
        firstName = view.findViewById(R.id.first_name);
        firstName.setText(MyProfile.getInstance().getFirstName());
        lastName = view.findViewById(R.id.last_name);
        lastName.setText(MyProfile.getInstance().getLastName());
        ((TextView)view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView)view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        dob = view.findViewById(R.id.dob);
        dob.setText(MyProfile.getInstance().getDob());
        dob.setOnClickListener(this);
        address1 = view.findViewById(R.id.address1);
        address1.setText(MyProfile.getInstance().getAddress1());
        address2 = view.findViewById(R.id.address2);
        address2.setText(MyProfile.getInstance().getAddress2());
        state = view.findViewById(R.id.state);
        state.setText(MyProfile.getInstance().getState());
        pinCode = view.findViewById(R.id.pin_code);
        pinCode.setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.save_profile).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:

                break;
            case R.id.dob:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }
}