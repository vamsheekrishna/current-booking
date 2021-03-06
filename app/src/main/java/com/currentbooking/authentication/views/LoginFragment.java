package com.currentbooking.authentication.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.LoginResponse;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private OnAuthenticationClickedListener mListener;
    private TextView mobileNoField;
    private TextView password;
    private String deviceKey;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
        sharedpreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener =null;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.forgot_password).setOnClickListener(this);
        mobileNoField = view.findViewById(R.id.user_id);
        password = view.findViewById(R.id.password);
        mobileNoField.requestFocus();
        if (sharedpreferences.contains("mobileNo")) {
            mobileNoField.setText(sharedpreferences.getString("mobileNo", ""));
            password.setText(sharedpreferences.getString("password", ""));
        }

        //mobileNoField.setText("8888888888");
        //password.setText("12345678");

        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS();
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                LoggerInfo.errorLog("getInstanceId failed", Objects.requireNonNull(task.getException()).getMessage());
                return;
            }
            deviceKey = Objects.requireNonNull(task.getResult()).getToken();
        });
        // encryptionSample();
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton("No", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgot_password) {
            mListener.goToForgotPassword();
        } else {
            String mobileNo = mobileNoField.getText().toString().trim();
            String passwordValue = password.getText().toString().trim();
            editor = sharedpreferences.edit();
            if (TextUtils.isEmpty(mobileNo) || TextUtils.isEmpty(passwordValue)) {
                showDialog("", getString(R.string.error_enter_mobile_and_password));
                return;
            }
            if (mobileNo.length() < 10) {
                showDialog("", getString(R.string.error_mobile));
                return;
            }
            if (!NetworkUtility.isNetworkConnected(requireActivity())) {
                showDialog("", getString(R.string.internet_fail));
                return;
            }
            progressDialog.show();
            LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            loginService.login(deviceKey, mobileNo, passwordValue).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                try {
                                    editor.putString("mobileNo", mobileNo);
                                    editor.putString("password", passwordValue);
                                    editor.apply();
                                    MyProfile.getInstance(data.getData().getProfileModel());
                                    if (MyProfile.getInstance().getDob() == null || MyProfile.getInstance().getDob().length() <= 0) {
                                        mListener.goToProfileActivity(true);
                                    } else {
                                        mListener.goToHomeActivity();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showDialog("", data.getMsg(), pObject -> {
                                    if (data.getMsg().contains("verify")) {
                                        mListener.validateOTP();
                                    } else if (data.getMsg().contains("mail_verify")) {
                                        mListener.validateMailOTP();
                                    }
                                });
                            }
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }
}