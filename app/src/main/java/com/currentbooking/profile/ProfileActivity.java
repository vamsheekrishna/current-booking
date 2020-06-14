package com.currentbooking.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.currentbooking.R;
import com.currentbooking.profile.ui.main.EditProfileFragment;
import com.currentbooking.profile.ui.main.OnProfileListener;
import com.currentbooking.profile.ui.main.ProfileFragment;
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseNavigationDrawerActivity;

public class ProfileActivity extends BaseNavigationDrawerActivity implements OnProfileListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.profile_activity);
        if (savedInstanceState == null) {
            addFragment(ProfileFragment.newInstance(),"",false);
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
}