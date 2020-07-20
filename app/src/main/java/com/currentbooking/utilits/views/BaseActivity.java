package com.currentbooking.utilits.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.BaseListener;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void replaceFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.base_container, baseFragment, fragment_id);
            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(fragment_id);
            }
            fragmentTransaction.commit();
    }

    protected void addFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.base_container, baseFragment, fragment_id);
        if(isAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment_id);
        }
        fragmentTransaction.commit();
    }
}
