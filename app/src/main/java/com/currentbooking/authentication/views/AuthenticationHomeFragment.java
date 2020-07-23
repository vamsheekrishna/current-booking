package com.currentbooking.authentication.views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.currentbooking.R;
import com.currentbooking.authentication.OnAuthenticationClickedListener;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class AuthenticationHomeFragment extends BaseFragment {

    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnAuthenticationClickedListener mListener;

    public AuthenticationHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnAuthenticationClickedListener)context;
    }

    public static AuthenticationHomeFragment newInstance(String param1, String param2) {
        AuthenticationHomeFragment fragment = new AuthenticationHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentication_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(LoginFragment.newInstance());
        baseFragments.add(RegistrationFragment.newInstance());
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = view.findViewById(R.id.pager);

        pagerAdapter = new ScreenSlidePagerAdapter(getActivity(), baseFragments);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        String[] titles = new String[]{"Login", "Registration"};
        // attaching tab mediator
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();

         mListener.showPermission();
    }
}