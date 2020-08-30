package com.currentbooking.wallet;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

import java.util.Objects;

public class AddMoneyWallet extends BaseFragment {

    /*EditText amount;
    Button payment;
    OnTicketBookingListener mListener;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_wallet);

       *//* amount=findViewById(R.id.amount);
        payment=findViewById(R.id.payment);*//*

        //addFragment(WalletFragment.newInstance((MyProfile.getInstance().getUserId()),"200"), "WalletFragment", false);


    }*/

    private AppCompatEditText amountField;

    private OnWalletListener mListener;

    public static AddMoneyWallet newInstance() {
        return new AddMoneyWallet();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnWalletListener) context;
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

        Button btnAddAmountField = view.findViewById(R.id.btn_add_money_field);
        btnAddAmountField.setOnClickListener(v -> {
            CommonUtils.hideKeyBoard(requireActivity(), btnAddAmountField);
            addAmountSelected();
        });
    }

    private void addAmountSelected() {
        String amount = Objects.requireNonNull(amountField.getText()).toString().trim();
        if(TextUtils.isEmpty(amount)) {
            showDialog(getString(R.string.amount_cannot_be_empty));
            return;
        }

        int amountValue = Utils.getIntegerValueFromString(amount);
        if(amountValue == 0) {
            showDialog(getString(R.string.amount_should_greater_than_zero));
            return;
        }
        mListener.gotoAddMoney(amount);
    }
}
