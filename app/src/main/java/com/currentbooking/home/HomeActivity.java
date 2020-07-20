package com.currentbooking.home;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.BaseListener;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class HomeActivity extends BaseNavigationDrawerActivity  implements BaseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addFragment(ServicesHomeFragment.newInstance(), "ServicesHomeFragment", false);
    }
}
