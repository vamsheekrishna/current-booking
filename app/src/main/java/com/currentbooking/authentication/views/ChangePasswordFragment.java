package com.currentbooking.authentication.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.UserData;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.ChangePasswordResponse;
import com.currentbooking.utilits.cb_api.responses.ForgotPasswordResponse;
import com.currentbooking.utilits.views.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnAuthenticationClickedListener mListener;
    private LoginService loginService;
    private TextView mobileNumber, oldPassword, newPassword, confirmPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.change_password).setOnClickListener(this);
        mobileNumber = view.findViewById(R.id.mobile_number);
        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        mobileNumber.setText("");
    }

    @Override
    public void onClick(View v) {
        String _mobileNumber = mobileNumber.getText().toString().trim();
        String _oldPassword = oldPassword.getText().toString().trim();
        String _newPassword = newPassword.getText().toString().trim();
        String _confirmPassword = confirmPassword.getText().toString().trim();
        if (!Utils.isValidMobile(_mobileNumber)) {
            showDialog("", getString(R.string.error_mobile_number));
        } else if (_oldPassword.length() <= 4) {
            showDialog("", getString(R.string.error_password));
        }  else if (_newPassword.length() <= 4) {
            showDialog("", getString(R.string.error_password));
        } else if (_confirmPassword.length() <= 4) {
            showDialog("", getString(R.string.error_password));
        } else if (!_confirmPassword.equals(_newPassword)) {
            showDialog("", getString(R.string.error_mismatch_password));
        } else if (_oldPassword.equals(_newPassword)) {
            showDialog("", getString(R.string.error_new_password));
        } else {
            progressDialog.show();
            loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            loginService.changePassword(MyProfile.getInstance().getUserId(), _mobileNumber, _oldPassword, _newPassword).enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getStatus().equalsIgnoreCase("success")) {
                            showDialog("",response.body().getMsg(), (dialog, which) -> {
                                getActivity().finish();
                            } );
                        } else {
                            showDialog("",response.body().getMsg());
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                    showDialog("",t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }
}