package com.currentbooking.authentication.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.RegistrationResponse;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.CustomLoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends BaseFragment implements View.OnClickListener {

    private OnAuthenticationClickedListener mListener;
    private TextView tvFirstName, tvLastName, tvMobileNumber, tvEmailID, tvPassword, tvConformPassword;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener) context;
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFirstName= view.findViewById(R.id.first_name);
        tvLastName = view.findViewById(R.id.last_name);
        tvMobileNumber = view.findViewById(R.id.mobile_no);
        tvEmailID = view.findViewById(R.id.email);
        tvPassword = view.findViewById(R.id.password);
        tvConformPassword = view.findViewById(R.id.conform_password);

        view.findViewById(R.id.registration).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fName = tvFirstName.getText().toString().trim();
        String lName = tvLastName.getText().toString().trim();
        String mobile = tvMobileNumber.getText().toString().trim();
        String email = tvEmailID.getText().toString().trim();
        String password = tvPassword.getText().toString().trim();
        String conformPassword = tvConformPassword.getText().toString().trim();

        if (!Utils.isValidWord(fName)) {
            showDialog("", getString(R.string.error_first_name));
            return;
        }
        if (!Utils.isValidWord(lName)) {
            showDialog("", getString(R.string.error_last_name));
            return;
        }
        if (!Utils.isValidMobile(mobile)) {
            showDialog("", getString(R.string.error_mobile));
            return;
        }
        if(!Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.error_mail));
            return;
        }
        if(TextUtils.isEmpty(password)) {
            showDialog("", getString(R.string.error_password));
            return;
        }
        if (password.length() <= 7) {
            showDialog("", getString(R.string.password_should_be_eight_characters));
            return;
        }
        if(TextUtils.isEmpty(conformPassword)) {
            showDialog("", getString(R.string.error_conformation_password));
            return;
        }
        if (conformPassword.length() <= 7) {
            showDialog("", getString(R.string.confirm_password_should_be_eight_characters));
            return;
        }
        if (!password.equals(conformPassword)) {
            showDialog("", getString(R.string.error_mismatch_password));
            return;
        }
        if(!NetworkUtility.isNetworkConnected(requireActivity())) {
            showDialog("", getString(R.string.internet_fail));
            return;
        }
        progressDialog.show();
        LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        loginService.registration(fName, lName, mobile, email, password, conformPassword).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    RegistrationResponse responseData = response.body();
                    if(Objects.requireNonNull(responseData).getStatus().equals("success")) {
                        mListener.validateOTP();
                        resetFields();
                    } else {
                        showDialog("", responseData.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                showDialog("", t.getMessage());
            }
        });
    }

    private void resetFields() {
        tvFirstName.setText("");
        tvLastName.setText("");
        tvMobileNumber.setText("");
        tvEmailID.setText("");
        tvPassword.setText("");
        tvConformPassword.setText("");
    }
}