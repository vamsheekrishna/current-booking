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

            if(data != null && data.getBoolean(getString(R.string.is_edit))) {
                addFragment(EditProfileFragment.newInstance(), "", false);
            } else {
                addFragment(ProfileFragment.newInstance(), "", false);
            }
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .commitNow();*/
        }
    }

    @Override
    public void goToEditProfile() {
        replaceFragment(EditProfileFragment.newInstance(),"edit profile", true);
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