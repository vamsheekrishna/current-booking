package com.currentbooking.authentication.views;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.currentbooking.R;
import com.currentbooking.SplashScreen;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.authentication.view_models.Authentication;
import com.currentbooking.profile.ProfileActivity;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class AuthenticationActivity extends BaseActivity implements OnAuthenticationClickedListener {
    private Authentication authentication;

    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();*/

        setContentView(R.layout.activity_home);

        if(savedInstanceState == null) {
            authentication = new ViewModelProvider(this).get(Authentication.class);
            addFragment(AuthenticationHomeFragment.newInstance("", ""), "AuthenticationHomeFragment", false);
        }
    }

    @Override
    public boolean showPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void goToTicketBookingActivity() {
        if (showPermission()) {
            Intent intent = new Intent(this, TicketBookingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void goToForgotPassword() {
        replaceFragment(ForgotPasswordFragment.newInstance("", ""), "ForgotPasswordFragment", true);
    }

    @Override
    public void validateOTP() {

    }

    @Override
    public void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(getString(R.string.is_edit), true);
        startActivity(intent);
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showPermission();
            }
        }
    }
}