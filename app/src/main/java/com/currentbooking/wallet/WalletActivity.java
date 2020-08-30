package com.currentbooking.wallet;


import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.OnTicketBookingListener;
import com.currentbooking.ticketbooking.WalletFragment;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseActivity;

public class WalletActivity extends BaseActivity implements OnWalletListener{
    EditText amount;
    Button payment;
    OnTicketBookingListener mListener;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

       /* amount=findViewById(R.id.amount);
        payment=findViewById(R.id.payment);*/
        context=this;
        addFragment(WalletFragment.newInstance((MyProfile.getInstance().getUserId()),"200"), "WalletFragment", false);


    }

    @Override
    public void gotoAddMoney(String userid) {

    }
}
