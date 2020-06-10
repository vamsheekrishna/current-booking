package com.currentbooking.authentication.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.authentication.view_models.Authentication;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.RegistrationResponse;
import com.currentbooking.utilits.views.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OnAuthenticationClickedListener mListener;
    private Authentication model;
    private TextView tvFirstName, tvLastName, tvUserID, tvMobileNumber, tvEmailID, tvPassword, tvConformPassword;
    private LoginService loginService;

    public RegistrationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(Authentication.class);

        tvFirstName= view.findViewById(R.id.first_name);
        tvLastName = view.findViewById(R.id.last_name);
        tvUserID = view.findViewById(R.id.user_id);
        tvMobileNumber = view.findViewById(R.id.mobile_no);
        tvEmailID = view.findViewById(R.id.email);
        tvPassword = view.findViewById(R.id.password);
        tvConformPassword = view.findViewById(R.id.conform_password);

        /*tvFirstName.setText("Anita");
        tvLastName.setText("navale");
        tvUserID.setText("anita123");
        tvMobileNumber.setText("8689823291");
        tvEmailID.setText("tadge@gmail.com");
        tvPassword.setText("anita123");
        tvConformPassword.setText("anita123");*/

        view.findViewById(R.id.registration).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fName = tvFirstName.getText().toString();
        String lName = tvLastName.getText().toString();
        String userID = tvUserID.getText().toString();
        String mobile = tvMobileNumber.getText().toString();
        String email = tvEmailID.getText().toString();
        String password = tvPassword.getText().toString();
        String conformPassword = tvConformPassword.getText().toString();

         /*else if(null != userID && ! (userID.length() > 5)) {
            showDialog("", getString(R.string.error_user_id));
        }*/
        if(!Utils.isValidWord(fName)) {
            showDialog("", getString(R.string.error_first_name));
        } else if(!Utils.isValidWord(lName)) {
            showDialog("", getString(R.string.error_last_name));
        } else if(!Utils.isValidMobile(mobile)) {
            showDialog("", getString(R.string.error_mobile));
        } else if(!Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.error_mail));
        } else if(!Utils.isValidEmail(email)) {
            showDialog("", getString(R.string.error_mail));
        } else if(null != password && ! (password.length() > 5)) {
            showDialog("", getString(R.string.error_password));
        } else if(null != conformPassword && ! (conformPassword.length() > 5)) {
            showDialog("", getString(R.string.error_conformation_password));
        } else if(!conformPassword.equals(conformPassword)) {
            showDialog("", getString(R.string.error_mismatch_password));
        } else {
            loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
            loginService.registration(fName, lName, mobile, email, password, conformPassword).enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if(response.isSuccessful()) {
                        RegistrationResponse responseData = response.body();
                        if(responseData.getStatus().equals("success")) {
                            requireActivity().onBackPressed();
                        } else {
                            showDialog("", responseData.getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    showDialog("", t.getMessage());
                }
            });
            // requireActivity().onBackPressed();
        }
    }
}