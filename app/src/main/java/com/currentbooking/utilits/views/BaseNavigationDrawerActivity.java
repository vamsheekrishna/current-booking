package com.currentbooking.utilits.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.currentbooking.R;
import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.ticketbooking.OptionSelection;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Menu menu;
    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    public NavigationView nv;
    protected LinearLayoutCompat signOut;
    private TextView textCartItemCount;
    private int mCartItemCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);
        setNavigationView();
        signOut.setOnClickListener(v -> {
            dl.closeDrawer(nv);
            startActivity(new Intent(BaseNavigationDrawerActivity.this, AuthenticationActivity.class));
            finish();
        });
    }
//    public void setContentView(int id) {
//        FrameLayout dynamicContent = findViewById(R.id.main_content);
//        View wizardView = getLayoutInflater().inflate(id, dynamicContent, false);
//        // add the inflated View to the select_bus_points
//        dynamicContent.addView(wizardView);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge_menu_drawer, menu);

        final MenuItem menuItem = menu.findItem(R.id.live_ticket);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseNavigationDrawerActivity.this,"Live ticket selected.", Toast.LENGTH_LONG).show();
            }
        });

        return true;
    }
    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setNavigationView() {
        dl = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        nv = findViewById(R.id.nav_view);
        signOut = nv.findViewById(R.id.sign_out_btn);
        nv.setNavigationItemSelectedListener(this);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_ticket_booking:
                startActivity(new Intent(this, TicketBookingActivity.class));
                finish();
                Toast.makeText(this,"nav_ticket_booking", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_history:
                Toast.makeText(this, "nav_history", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_my_ticket:
                Toast.makeText(this, "nav_my_ticket", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_help:
                Toast.makeText(this, "nav_help", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_options_selection:
                replaceFragment(OptionSelection.newInstance("", ""), "OptionSelection", true);
                break;
        }
        dl.closeDrawer(nv);
        return false;
    }

}
