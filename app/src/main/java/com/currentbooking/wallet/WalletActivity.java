package com.currentbooking.wallet;


import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.OnTicketBookingListener;
import com.currentbooking.ticketbooking.WalletFragment;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseActivity;

public class WalletActivity extends BaseActivity implements OnWalletListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //addFragment(WalletFragment.newInstance((MyProfile.getInstance().getUserId()),"200"), "WalletFragment", false);
        addFragment(AddMoneyWallet.newInstance(), "AddMoneyWallet", false);
    }

    @Override
    public void gotoAddMoney(String amount) {
        addFragment(WalletFragment.newInstance(amount), "WalletFragment", true);
    }
}
