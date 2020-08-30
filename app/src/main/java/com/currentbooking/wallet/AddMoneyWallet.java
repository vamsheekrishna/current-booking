package com.currentbooking.wallet;


import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.OnTicketBookingListener;
import com.currentbooking.ticketbooking.WalletFragment;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class AddMoneyWallet extends BaseFragment {

    EditText amount;
    Button payment;
    OnTicketBookingListener mListener;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_wallet);

       /* amount=findViewById(R.id.amount);
        payment=findViewById(R.id.payment);*/

        //addFragment(WalletFragment.newInstance((MyProfile.getInstance().getUserId()),"200"), "WalletFragment", false);


    }


}
