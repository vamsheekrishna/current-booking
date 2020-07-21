package com.currentbooking.utilits.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.currentbooking.CurrentBookingApplication;
import com.currentbooking.R;
import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.help.HelpActivity;
import com.currentbooking.profile.ProfileActivity;
import com.currentbooking.ticketbooking.BaseListener;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.ticketbookinghistory.TicketBookingHistoryActivity;
import com.currentbooking.utilits.CommonUtils;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.HttpsTrustManager;
import com.currentbooking.utilits.MyProfile;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Locale;

public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, BaseListener {

    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    private TextView textCartItemCount;
    private NetworkImageView ivProfileImageField;
    private View badgeBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);
        setNavigationView();
        loadUIComponents();
    }

    private void loadUIComponents() {
        ivProfileImageField = findViewById(R.id.iv_profile_image_field);
        TextView tvEmailIdField = findViewById(R.id.email_address_field);
        TextView tvPhoneNoField = findViewById(R.id.phone_number_field);
        TextView tvUserNameField = findViewById(R.id.user_name_field);
        TextView tvUserAgeField = findViewById(R.id.user_age_field);

        ProgressBarCircular progressBarCircular = findViewById(R.id.profile_circular_field);
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            String imageUrl = myProfile.getProfileImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                HttpsTrustManager.allowAllSSL();
                ImageLoader imageLoader = CurrentBookingApplication.getInstance().getImageLoader();
                imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        progressBarCircular.setVisibility(View.GONE);
                        Bitmap bitmap = response.getBitmap();
                        ivProfileImageField.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarCircular.setVisibility(View.GONE);
                        ivProfileImageField.setImageResource(R.drawable.avatar);
                    }
                });
                ivProfileImageField.setImageUrl(imageUrl, imageLoader);

            }
            String emailID = myProfile.getEmail();
            if (!TextUtils.isEmpty(emailID)) {
                tvEmailIdField.setText(emailID);
            }
            String phoneNumber = myProfile.getMobileNumber();
            if (!TextUtils.isEmpty(phoneNumber)) {
                tvPhoneNoField.setText(phoneNumber);
            }
            String userName = String.format("%s %s", myProfile.getFirstName(), myProfile.getLastName());
            if (!TextUtils.isEmpty(userName)) {
                tvUserNameField.setText(userName);
            }
            String dateOfBirth = MyProfile.getInstance().getDob();
            if (!TextUtils.isEmpty(dateOfBirth)) {
                Calendar dateOfBirthCalendar = DateUtilities.getCalendarFromDate2(dateOfBirth);
                int age = DateUtilities.getAgeDifference(dateOfBirthCalendar);
                String ageDifference = String.format(Locale.getDefault(), "%d yrs", age);
                tvUserAgeField.setText(ageDifference);
            }
            myProfile.getUserProfileImage().observe(this, bitmap -> {
                Bitmap newBitmap = CommonUtils.getCircularBitmap(bitmap);
                ivProfileImageField.setImageBitmap(newBitmap);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge_menu_drawer, menu);

        final MenuItem menuItem = menu.findItem(R.id.live_ticket);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        badgeBase = actionView.findViewById(R.id.badge_base);
        showBadge(true);

        actionView.setOnClickListener(v -> startActivity(new Intent(BaseNavigationDrawerActivity.this, TicketBookingHistoryActivity.class)));

        return true;
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
        findViewById(R.id.change_password_layout_field).setOnClickListener(this);

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
                mDrawerLayout.closeDrawer(navigationView);
                Intent intent = new Intent(BaseNavigationDrawerActivity.this, TicketBookingHistoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.help_layout_field:
                gotoHelpScreen();
                break;
            case R.id.logout_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, AuthenticationActivity.class));
                break;
            case R.id.my_profile_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, ProfileActivity.class));
                break;
            case R.id.change_password_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                intent = new Intent(this, AuthenticationActivity.class);
                intent.putExtra(getString(R.string.change_password), true);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                break;
        }
        mDrawerLayout.closeDrawer(navigationView);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    protected void replaceFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        super.replaceFragment(baseFragment, fragment_id, isAddToBackStack);
    }

    protected void addFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
       super.addFragment(baseFragment,fragment_id,isAddToBackStack);
    }

    @Override
    public void showHamburgerIcon(boolean b) {
        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);   //hide hamburger button
        }*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(b);
        }

    }

    @Override
    public void showBadge(boolean b) {
        if(null != badgeBase) {
            if (b) {
                badgeBase.setVisibility(View.VISIBLE);
                Integer value = MyProfile.getInstance().getCurrentBookingTicketCount().getValue();
                if(value!=null && value > 0)
                    textCartItemCount.setText(String.valueOf(Math.min(value, 99)));
                else
                    badgeBase.setVisibility(View.GONE);

            } else {
                badgeBase.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void updateBadgeCount() {
        if (textCartItemCount != null) {
            MyProfile myProfile = MyProfile.getInstance();
            if (myProfile != null) {
                Integer value = myProfile.getCurrentBookingTicketCount().getValue();
                if (value != null) {
                    textCartItemCount.setText(String.valueOf(Math.min(value, 99)));
                }
            }
        }
    }

    private void gotoHelpScreen() {
        startActivity(new Intent(this, HelpActivity.class));
    }
}
