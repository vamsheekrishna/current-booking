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
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.LoginResponse;
import com.currentbooking.utilits.views.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnAuthenticationClickedListener mListener;
    private LoginService loginService;
    private TextView userName;
    private TextView password;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.forgot_password).setOnClickListener(this);
        userName = view.findViewById(R.id.user_id);
        password = view.findViewById(R.id.password);
        userName.setText("7416226233");
        password.setText("12345");
        userName.setText("8686378737");
        password.setText("12345678");

        // encryptionSample();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgot_password) {
            mListener.goToForgotPassword();
        } else {
            String userNameValue = userName.getText().toString().trim();
            String passwordValue = password.getText().toString().trim();

            if (TextUtils.isEmpty(userNameValue)) {
                showDialog("", getString(R.string.user_id_cannot_be_empty));
            } else if (TextUtils.isEmpty(passwordValue)) {
                showDialog("", getString(R.string.password_cannot_be_empty));
            } else {

                progressDialog.show();
                loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
                loginService.login(userNameValue, passwordValue).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse data = response.body();
                            if(data != null) {
                                if (data.getStatus().equalsIgnoreCase("success")) {
                                    try {
                                        MyProfile.getInstance(data.getData().getProfileModel());

                                        // MyProfile.getInstance().updateLiveTickets(progressDialog);
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
                                        if(data.getMsg().contains("verify")) {
                                            mListener.validateOTP();
                                        }
                                    });
                                }
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        showDialog("", t.getMessage());
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

}