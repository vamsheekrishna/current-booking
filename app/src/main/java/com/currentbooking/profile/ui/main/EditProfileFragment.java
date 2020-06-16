package com.currentbooking.profile.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.currentbooking.R;
import com.currentbooking.interfaces.DateTimeInterface;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    // private ProfileViewModel mViewModel;
    OnProfileListener mListener;
    private AppCompatEditText firstName;
    private AppCompatEditText lastName;
    private AppCompatEditText address1;
    private AppCompatEditText address2;
    private AppCompatEditText state;
    private AppCompatEditText pinCode;
    AppCompatTextView dob;
    private Calendar dateOfBirthCalendar;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnProfileListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dateOfBirthCalendar = Calendar.getInstance();
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Edit Profile");
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
        lastName = view.findViewById(R.id.last_name);
        firstName.setText(MyProfile.getInstance().getFirstName());
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
                //
                break;
            case R.id.dob:
                dateOfPickerSelected();
            default:
                break;
        }
    }

    private void dateOfPickerSelected() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle extras = new Bundle();
        extras.putInt("Year", dateOfBirthCalendar.get(Calendar.YEAR));
        extras.putInt("Month", dateOfBirthCalendar.get(Calendar.MONTH));
        extras.putInt("DayOfMonth", dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerFragment.setArguments(extras);
        datePickerFragment.setCallBack(new DateTimeInterface() {
            @Override
            public void onTimeSet(Integer hourOfDay, Integer minute, String amOrPm) {

            }

            @Override
            public void onDateSet(Integer year, Integer month, Integer dayOfMonth) {
                dateOfBirthCalendar.set(Calendar.YEAR, year);
                dateOfBirthCalendar.set(Calendar.MONTH, month);
                dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                dob.setText(sdf.format(dateOfBirthCalendar.getTime()));
            }
        });
        if (!Objects.requireNonNull(getActivity()).isFinishing()) {
            datePickerFragment.show(getActivity().getSupportFragmentManager(), DatePickerFragment.class.getName());
        }
    }
}