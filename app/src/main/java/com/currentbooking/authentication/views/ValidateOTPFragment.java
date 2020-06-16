package com.currentbooking.authentication.views;

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
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.LoginResponse;
import com.currentbooking.utilits.cb_api.responses.ValidateOTP;
import com.currentbooking.utilits.views.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateOTPFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OnAuthenticationClickedListener mListener;
    private LoginService loginService;
    private TextView otpView;
    private TextView password;

    public ValidateOTPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
    }

    public static ValidateOTPFragment newInstance(String param1, String param2) {
        ValidateOTPFragment fragment = new ValidateOTPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        return inflater.inflate(R.layout.validate_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.validate_otp).setOnClickListener(this);
        otpView = view.findViewById(R.id.otp_placeholder);
        otpView.setText("8919251921");
    }

    @Override
    public void onClick(View v) {
        String otp = otpView.getText().toString().trim();

        if (TextUtils.isEmpty(otp)) {
            showDialog("", getString(R.string.error_enter_otp));
        } else {
            progressDialog.show();
            loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            loginService.validateOTP("userNameValue", otp).enqueue(new Callback<ValidateOTP>() {
                @Override
                public void onResponse(Call<ValidateOTP> call, Response<ValidateOTP> response) {
                    if(response.isSuccessful()) {
                        ValidateOTP data = response.body();
                        assert data != null;
                        if (data.getStatus().equalsIgnoreCase("success")) {

                        } else {
                            showDialog("", data.getMsg());
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ValidateOTP> call, Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }
}