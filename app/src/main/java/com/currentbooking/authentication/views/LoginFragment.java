package com.currentbooking.authentication.views;

import android.annotation.SuppressLint;
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
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.LoginResponse;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private OnAuthenticationClickedListener mListener;
    private TextView mobileNoField;
    private TextView password;
    private String deviceKey;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
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
        mobileNoField.setText("7039191834");
        password.setText("12345678");

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                LoggerInfo.errorLog("getInstanceId failed", Objects.requireNonNull(task.getException()).getMessage());
                return;
            }
            deviceKey = Objects.requireNonNull(task.getResult()).getToken();
        });
        // encryptionSample();
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgot_password) {
            mListener.goToForgotPassword();
        } else {
            String mobileNo = mobileNoField.getText().toString().trim();
            String passwordValue = password.getText().toString().trim();

            if (TextUtils.isEmpty(mobileNo) || TextUtils.isEmpty(passwordValue)) {
                showDialog("", getString(R.string.error_enter_mobile_and_password));
                return;
            }
            if (mobileNo.length() < 10) {
                showDialog("", getString(R.string.error_mobile));
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
                                    MyProfile.getInstance(data.getData().getProfileModel());
                                    if (MyProfile.getInstance().getDob() == null || MyProfile.getInstance().getDob().length() <= 0) {
                                        mListener.goToProfileActivity();
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