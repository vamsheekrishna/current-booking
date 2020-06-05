package com.currentbooking.authentication.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.currentbooking.utilits.views.BaseFragment;

import java.util.ArrayList;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    ArrayList<BaseFragment> baseFragments;
    public ScreenSlidePagerAdapter(FragmentActivity context, ArrayList<BaseFragment> baseFragments) {
        super(context);
        this.baseFragments = baseFragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return baseFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return baseFragments.size();
    }
}
