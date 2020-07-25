package com.currentbooking.authentication.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.currentbooking.R;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.ResendOTPResponse;
import com.currentbooking.utilits.cb_api.responses.ValidateOTP;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateOTPFragment extends BaseFragment implements View.OnClickListener {


    private TextView otpView;
    private TextView mobileNumber;

    public ValidateOTPFragment() {
        // Required empty public constructor
    }

    public static ValidateOTPFragment newInstance() {
        return new ValidateOTPFragment();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment

        return inflater.inflate(R.layout.validate_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.validate_otp).setOnClickListener(this);
        view.findViewById(R.id.resend).setOnClickListener(this);
        otpView = view.findViewById(R.id.otp_placeholder);
        mobileNumber = view.findViewById(R.id.mobile_no);
    }

    @Override
    public void onClick(View v) {
        String otp = otpView.getText().toString().trim();
        String _mobileNumber = mobileNumber.getText().toString().trim();
        if (!Utils.isValidMobile(_mobileNumber)) {
            showDialog("", getString(R.string.error_mobile_number));
        } else {
            progressDialog.show();
            LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            if (v.getId() == R.id.resend) {
                loginService.resendOTP(_mobileNumber).enqueue(new Callback<ResendOTPResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ResendOTPResponse> call, @NotNull Response<ResendOTPResponse> response) {
                        if(response.isSuccessful()) {
                            showDialog("", Objects.requireNonNull(response.body()).getMsg());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResendOTPResponse> call, @NotNull Throwable t) {
                        showDialog("", t.getMessage());
                        progressDialog.dismiss();
                    }
                });
            } else {
                if (TextUtils.isEmpty(otp)) {
                    showDialog("", getString(R.string.error_enter_otp));
                    progressDialog.dismiss();
                } else {
                    loginService.validateOTP(_mobileNumber, otp).enqueue(new Callback<ValidateOTP>() {
                        @Override
                        public void onResponse(@NotNull Call<ValidateOTP> call, @NotNull Response<ValidateOTP> response) {
                            if (response.isSuccessful()) {
                                ValidateOTP data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equalsIgnoreCase("success")) {
                                        showDialog("", data.getMsg(), pObject -> Objects.requireNonNull(getActivity()).onBackPressed());
                                    } else {
                                        showDialog("", data.getMsg());
                                    }
                                }
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(@NotNull Call<ValidateOTP> call, @NotNull Throwable t) {
                            showDialog("", t.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        }
    }
}