package com.currentbooking.authentication.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.utilits.Constants;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.ChangePasswordResponse;
import com.currentbooking.utilits.views.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {

    private TextView oldPassword, newPassword, confirmPassword;
    private OnAuthenticationClickedListener mListener;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
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
        //mobileNumber = view.findViewById(R.id.mobile_number);
        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        //mobileNumber.setText("");
    }

    @Override
    public void onClick(View v) {
        //String _mobileNumber = mobileNumber.getText().toString().trim();
        String _oldPassword = oldPassword.getText().toString().trim();
        String _newPassword = newPassword.getText().toString().trim();
        String _confirmPassword = confirmPassword.getText().toString().trim();
        if (_oldPassword.length() <= 4) {
            showDialog("", getString(R.string.error_password));
        } else if (_newPassword.length() <= 4) {
            showDialog("", getString(R.string.error_password));
        } else if (_confirmPassword.length() <= 4) {
            showDialog("", getString(R.string.error_password));
        } else if (!_confirmPassword.equals(_newPassword)) {
            showDialog("", getString(R.string.error_mismatch_password));
        } else if (_oldPassword.equals(_newPassword)) {
            showDialog("", getString(R.string.error_new_password));
        } else {
            progressDialog.show();
            String mobileNumber = MyProfile.getInstance().getMobileNumber();
            LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            loginService.changePassword(MyProfile.getInstance().getUserId(), mobileNumber, _oldPassword, _newPassword).enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(@NotNull Call<ChangePasswordResponse> call, @NotNull Response<ChangePasswordResponse> response) {
                    if (response.isSuccessful()) {
                        ChangePasswordResponse body = response.body();
                        if(body != null) {
                            if (body.getStatus().equalsIgnoreCase("success")) {
                                showDialog("", body.getMsg(), pObject -> {
                                    if (pObject instanceof String) {
                                        if (((String) pObject).equalsIgnoreCase(Constants.TAG_SUCCESS)) {
                                            gotoAuthenticationActivity();
                                        }
                                    }
                                });
                            } else {
                                showDialog("", body.getMsg());
                            }
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<ChangePasswordResponse> call, @NotNull Throwable t) {
                    showDialog("",t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void gotoAuthenticationActivity() {
        mListener.goToLogout(getActivity());
        /*Intent intent = new Intent(requireActivity(), AuthenticationActivity.class);
        intent.putExtra(getString(R.string.change_password), true);
        startActivity(intent);
        requireActivity().finish();*/
    }
}