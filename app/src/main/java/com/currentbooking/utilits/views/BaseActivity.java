package com.currentbooking.utilits.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.currentbooking.R;
import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.ticketbooking.BaseListener;
import com.currentbooking.utilits.MyProfile;

public class BaseActivity extends AppCompatActivity{

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
    public void goToLogout(Context context) {
        MyProfile.getInstance().resetMyProfile();
        Intent i = new Intent(context, AuthenticationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
