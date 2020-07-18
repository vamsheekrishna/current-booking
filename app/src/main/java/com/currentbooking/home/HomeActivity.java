package com.currentbooking.home;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.utilits.views.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addFragment(ServicesHomeFragment.newInstance(), "ServicesHomeFragment", false);
    }
}
