package com.currentbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.profile.ProfileActivity;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.UserData;
import com.currentbooking.utilits.views.BaseActivity;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();*/

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(null == MyProfile.getInstance()) {
                    Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                    startActivity(intent);
                } else {
                    if (MyProfile.getInstance().getDob() == null || MyProfile.getInstance().getDob().length() <= 0) {
                        Intent intent = new Intent(SplashScreen.this, ProfileActivity.class);
                        intent.putExtra(getString(R.string.is_edit), true);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(SplashScreen.this, TicketBookingActivity.class));
                    }
                }
                finish();
            }
        },1000);

    }
}