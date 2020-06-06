package com.currentbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.utilits.UserData;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(null == UserData.getInstance()) {
                    startActivity(new Intent(SplashScreen.this, AuthenticationActivity.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, TicketBookingActivity.class));
                }
                finish();
            }
        },1000);

    }
}