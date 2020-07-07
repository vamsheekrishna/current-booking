package com.currentbooking.home;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.authentication.views.AuthenticationHomeFragment;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class HomeActivity extends BaseNavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addFragment(ServicesHomeFragment.newInstance("", ""), "ServicesHomeFragment", false);
    }


}
