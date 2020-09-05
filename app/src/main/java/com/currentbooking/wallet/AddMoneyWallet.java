package com.currentbooking.wallet;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.currentbooking.R;
import com.currentbooking.profile.ui.main.OnProfileListener;
import com.currentbooking.profile.ui.main.ProfileFragment;
import com.currentbooking.ticketbooking.OnTicketBookingListener;
import com.currentbooking.ticketbooking.WalletFragment;
import com.currentbooking.utilits.CommonUtils;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyWallet extends BaseFragment {


    private AppCompatEditText amountField;
    String billing_name, billing_email, billing_tel;
    private TextView name,textview_balance;
    private OnWalletListener mListener;
    MyWalletBalance balance;
    String limit="";
    public static AddMoneyWallet newInstance() {
        return new AddMoneyWallet();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnWalletListener) context;
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.ticket_booking));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_money_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amountField = view.findViewById(R.id.amount_field);
        name=view.findViewById(R.id.name);
        textview_balance=view.findViewById(R.id.balance);

        billing_name = MyProfile.getInstance().getFirstName();//"shweta";
        billing_email = MyProfile.getInstance().getEmail(); // "shwetadalvi9@gmail.com";
        billing_tel = MyProfile.getInstance().getMobileNumber();
        //name.setText("Name:"+billing_name);
        Button btnAddAmountField = view.findViewById(R.id.btn_add_money_field);
        btnAddAmountField.setOnClickListener(v -> {
            CommonUtils.hideKeyBoard(requireActivity(), btnAddAmountField);
            addAmountSelected();
        });
        TicketBookingServices ticketBookingService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        ticketBookingService.getWalletBalance(MyProfile.getInstance().getUserId(),"").enqueue(new Callback<WalletBalance>() {
            @Override
            public void onResponse(Call<WalletBalance> call, Response<WalletBalance> response) {
                WalletBalance data = response.body();
                if(response.isSuccessful()) {
                    assert data != null;
                    if(data.getStatus().equalsIgnoreCase("success")) {
                        assert response.body() != null;
                        balance = response.body().getAvailableBalance();
                        textview_balance.setText("Wallet Balance: "+balance.getwallet_balance());
                        limit=balance.getLimit();
                    } else {
                        showDialog("", data.getMsg());
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WalletBalance> call, Throwable t) {
                showDialog("", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void addAmountSelected() {
        String amount = Objects.requireNonNull(amountField.getText()).toString().trim();
        if(TextUtils.isEmpty(amount)) {
            showDialog(getString(R.string.amount_cannot_be_empty));
            return;
        }
        if(amount.contains("-")){
            showDialog(getString(R.string.invalid_amount));
           return;
        }

        int amountValue = Utils.getIntegerValueFromString(amount);
        int amountValuelimit = Utils.getIntegerValueFromString(limit);
        if(amountValue >= amountValuelimit) {
            showDialog(getString(R.string.Exceed_amount));
            return;
        }
        if(amountValue == 0) {
            showDialog(getString(R.string.amount_should_greater_than_zero));
            return;
        }

        mListener.gotoAddMoney(amount);
    }
}
