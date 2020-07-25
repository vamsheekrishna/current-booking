package com.currentbooking.authentication.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.home.HomeActivity;
import com.currentbooking.profile.ProfileActivity;
import com.currentbooking.utilits.views.BaseActivity;

public class AuthenticationActivity extends BaseActivity implements OnAuthenticationClickedListener {

    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean isChangedPassword = extras.getBoolean(getString(R.string.change_password));
            if(isChangedPassword) {
                addFragment(AuthenticationHomeFragment.newInstance(), "AuthenticationHomeFragment", false);
            } else {
                addFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment", false);
            }
        } else if (savedInstanceState == null) {
            addFragment(AuthenticationHomeFragment.newInstance(), "AuthenticationHomeFragment", false);
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
    public void goToHomeActivity() {
        if (showPermission()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void goToForgotPassword() {
        replaceFragment(ForgotPasswordFragment.newInstance(), "ForgotPasswordFragment", true);
    }

    @Override
    public void validateOTP() {
        replaceFragment(ValidateOTPFragment.newInstance(), "ValidateOTPFragment", true);
    }

    @Override
    public void changePassword() {

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