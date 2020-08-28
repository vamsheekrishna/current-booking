package com.currentbooking.authentication.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.currentbooking.R;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.ForgotPasswordResponse;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {

    // TODO: Rename and change types of parameters
    private TextView mobileNumber;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.send_otp).setOnClickListener(this);
        mobileNumber = view.findViewById(R.id.mobile_number);
    }

    @Override
    public void onClick(View v) {
        String _mobileNumber = mobileNumber.getText().toString().trim();

        if (!Utils.isValidMobile(_mobileNumber)) {
            showDialog("", getString(R.string.error_mobile_number));
            return;
        }

        if (!NetworkUtility.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.internet_fail));
            return;
        }

        progressDialog.show();
        LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        loginService.forgotPassword(_mobileNumber).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NotNull Call<ForgotPasswordResponse> call, @NotNull Response<ForgotPasswordResponse> response) {
                ForgotPasswordResponse body = response.body();
                if (body != null) {
                    showDialog("", body.getMsg());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ForgotPasswordResponse> call, @NotNull Throwable t) {
                showDialog("", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}