package com.currentbooking.authentication.views;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.authentication.view_models.Authentication;
import com.currentbooking.utilits.views.BaseActivity;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class AuthenticationActivity extends BaseActivity implements OnAuthenticationClickedListener {
    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();*/

        setContentView(R.layout.activity_authentication);

        authentication = new ViewModelProvider(this).get(Authentication.class);
        ArrayList<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(LoginFragment.newInstance("",""));
        baseFragments.add(RegistrationFragment.newInstance("",""));
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);

        pagerAdapter = new ScreenSlidePagerAdapter(this,baseFragments);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        String[] titles = new String[]{"Login", "Registration"};
        // attaching tab mediator
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void goToTicketBookingActivity() {

    }

    @Override
    public void goToForgotPassword() {

    }

    @Override
    public void goToTabView() {

    }
}