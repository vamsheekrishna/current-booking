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
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.ResponseUpdateProfile;
import com.currentbooking.utilits.views.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    // private ProfileViewModel mViewModel;
    OnProfileListener mListener;
    private AppCompatEditText etFirstName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etAddress1;
    private AppCompatEditText etAddress2;
    private AppCompatEditText etState;
    private AppCompatEditText etPinCode, email;

    AppCompatTextView dob, male, female;
    private Calendar dateOfBirthCalendar;
    String gender = "Male";
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
        etFirstName = view.findViewById(R.id.first_name);
        etLastName = view.findViewById(R.id.last_name);
        etFirstName.setText(MyProfile.getInstance().getFirstName());
        etLastName.setText(MyProfile.getInstance().getLastName());
        ((TextView) view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView) view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        dob = view.findViewById(R.id.dob);
        dob.setText(MyProfile.getInstance().getDob());
        dob.setOnClickListener(this);
        male= view.findViewById(R.id.male);
        male.setOnClickListener(this);
        female = view.findViewById(R.id.female);
        female.setOnClickListener(this);
        etAddress1 = view.findViewById(R.id.address1);
        etAddress1.setText(MyProfile.getInstance().getAddress1());
        etAddress2 = view.findViewById(R.id.address2);
        etAddress2.setText(MyProfile.getInstance().getAddress2());
        etState = view.findViewById(R.id.state);
        etState.setText(MyProfile.getInstance().getState());
        email = view.findViewById(R.id.email);
        email.setText(MyProfile.getInstance().getEmail());
        etPinCode = view.findViewById(R.id.pin_code);
        etPinCode.setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.save_profile).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_profile:
                saveSelected();
                break;
            case R.id.dob:
                dateOfPickerSelected();
            case R.id.female:
                gender = "Female";
                female.setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_bg_selected));
                female.setTextColor(getResources().getColor(R.color.white));
                male.setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_bg));
                male.setTextColor(getResources().getColor(R.color.colorAccent));

                break;
            case R.id.male:
                gender = "Male";
                male.setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_bg_selected));
                male.setTextColor(getResources().getColor(R.color.white));
                female.setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_bg));
                female.setTextColor(getResources().getColor(R.color.colorAccent));

                break;
            default:
                break;
        }
    }

    private void saveSelected() {

        String fName = Objects.requireNonNull(etFirstName.getText()).toString().trim();
        String lName = Objects.requireNonNull(etLastName.getText()).toString().trim();
        String _etAddress1 = Objects.requireNonNull(etAddress1.getText()).toString().trim();
        String _etAddress2 = Objects.requireNonNull(etAddress2.getText()).toString().trim();
        String _etState =  Objects.requireNonNull(etState.getText()).toString().trim();
        String _etPinCode = Objects.requireNonNull(etPinCode.getText()).toString().trim();
        String _email = Objects.requireNonNull(email.getText()).toString().trim();
        String _dob = Objects.requireNonNull(dob.getText()).toString().trim();
        if (!Utils.isValidWord(fName)) {
            showDialog("", getString(R.string.error_first_name));
        } else if (!Utils.isValidWord(lName)) {
            showDialog("", getString(R.string.error_last_name));
        } else {
            LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            loginService.updateProfile(MyProfile.getInstance().getUserId(), fName, lName, gender, _email,
                    _etAddress1, _etAddress2, _etPinCode, _dob,"sample/sample", _etState ).enqueue(new Callback<ResponseUpdateProfile>() {
                @Override
                public void onResponse(Call<ResponseUpdateProfile> call, Response<ResponseUpdateProfile> response) {
                        if(response.isSuccessful()) {
                            assert response.body() != null;
                            if(response.body().getStatus().equalsIgnoreCase("success")) {
                                showDialog("", response.body().getMsg());
                            } else {
                                showDialog("", response.body().getMsg());
                            }
                        }
                }

                @Override
                public void onFailure(Call<ResponseUpdateProfile> call, Throwable t) {
                    showDialog("", t.getMessage());
                }
            });
            // requireActivity().onBackPressed();
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