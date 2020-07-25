package com.currentbooking.profile;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.profile.ui.main.EditProfileFragment;
import com.currentbooking.profile.ui.main.OnProfileListener;
import com.currentbooking.profile.ui.main.ProfileFragment;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;


public class ProfileActivity extends BaseNavigationDrawerActivity implements OnProfileListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.profile_activity);
        if (savedInstanceState == null) {
            Bundle data = getIntent().getExtras();
            if (data != null) {
                boolean firstTimeUserLoggedIn = data.getBoolean(getString(R.string.first_time_user_logged_in));
                if (data.getBoolean(getString(R.string.is_edit))) {
                    addFragment(EditProfileFragment.newInstance(firstTimeUserLoggedIn), "EditProfileFragment", false);
                } else {
                    addFragment(ProfileFragment.newInstance(), "ProfileFragment", false);
                }
            } else {
                addFragment(ProfileFragment.newInstance(), "ProfileFragment", false);
            }
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .commitNow();*/
        }
    }

    @Override
    public void goToEditProfile() {
        replaceFragment(EditProfileFragment.newInstance(false), "edit profile", true);
    }

    @Override
    public void goToViewProfile() {
        replaceFragment(ProfileFragment.newInstance(), "profile", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}