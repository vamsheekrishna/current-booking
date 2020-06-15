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
import androidx.drawerlayout.widget.DrawerLayout;

import com.currentbooking.R;
import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.profile.ProfileActivity;
import com.currentbooking.profile.ui.main.ProfileFragment;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    //protected LinearLayoutCompat signOut;
    private TextView textCartItemCount;
    private int mCartItemCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);
        setNavigationView();
        /*signOut.setOnClickListener(v -> {

        });*/
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

        actionView.setOnClickListener(v -> Toast.makeText(BaseNavigationDrawerActivity.this, "Live ticket selected.", Toast.LENGTH_LONG).show());

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
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        navigationView = findViewById(R.id.nav_view);
        //signOut = navigationView.findViewById(R.id.sign_out_btn);
        navigationView.setNavigationItemSelectedListener(this);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.book_ticket_layout_field).setOnClickListener(this);
        findViewById(R.id.booking_history_layout_field).setOnClickListener(this);
        findViewById(R.id.help_layout_field).setOnClickListener(this);
        findViewById(R.id.logout_layout_field).setOnClickListener(this);
        findViewById(R.id.my_profile_layout_field).setOnClickListener(this);

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
                //Toast.makeText(this,"nav_ticket_booking", Toast.LENGTH_LONG).show();
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
            case R.id.my_profile_layout_field:
                startActivity(new Intent(this, ProfileFragment.class));
                finish();
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawerLayout.closeDrawer(navigationView);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_ticket_layout_field:
                startActivity(new Intent(this, TicketBookingActivity.class));
                finish();
                // Toast.makeText(this, "nav_ticket_booking", Toast.LENGTH_LONG).show();
                break;
            case R.id.booking_history_layout_field:
                Toast.makeText(this, "nav_history", Toast.LENGTH_LONG).show();
                break;
            case R.id.help_layout_field:
                Toast.makeText(this, "nav_help", Toast.LENGTH_LONG).show();
                break;
            case R.id.logout_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, AuthenticationActivity.class));
                finish();
                break;
            case R.id.my_profile_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, ProfileActivity.class));
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawerLayout.closeDrawer(navigationView);
    }
}
