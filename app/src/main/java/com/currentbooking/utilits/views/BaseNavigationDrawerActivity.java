package com.currentbooking.utilits.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.authentication.view_models.Authentication;
import com.currentbooking.authentication.views.AuthenticationActivity;
import com.currentbooking.profile.ProfileActivity;
import com.currentbooking.profile.ui.main.ProfileFragment;
import com.currentbooking.ticketbooking.TicketBookingActivity;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.ticketbookinghistory.TicketBookingHistoryActivity;
import com.currentbooking.utilits.CircleTransform;
import com.currentbooking.utilits.CommonUtils;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.MyProfile;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    //protected LinearLayoutCompat signOut;
    private TextView textCartItemCount;
    private int mCartItemCount = 1;
    private AppCompatImageView ivProfileImageField;

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

        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            String imageUrl = myProfile.getProfileImage();
            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.get().load(imageUrl).placeholder(R.drawable.avatar).error(R.drawable.avatar).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).
                        transform(new CircleTransform()).into(ivProfileImageField);
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
        }

        MyProfile.getInstance().getUserProfileImage().observe(this, bitmap -> {
            Bitmap newBitmap = CommonUtils.getCircularBitmap(bitmap);
            ivProfileImageField.setImageBitmap(newBitmap);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge_menu_drawer, menu);

        final MenuItem menuItem = menu.findItem(R.id.live_ticket);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(v -> {
            startActivity(new Intent(BaseNavigationDrawerActivity.this, TicketBookingHistoryActivity.class));
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
        findViewById(R.id.logout_change_password_field).setOnClickListener(this);

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
                Toast.makeText(this, "nav_help", Toast.LENGTH_LONG).show();
                break;
            case R.id.logout_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, AuthenticationActivity.class));
                break;
            case R.id.my_profile_layout_field:
                mDrawerLayout.closeDrawer(navigationView);
                startActivity(new Intent(BaseNavigationDrawerActivity.this, ProfileActivity.class));
                break;
            case R.id.logout_change_password_field:
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
}
