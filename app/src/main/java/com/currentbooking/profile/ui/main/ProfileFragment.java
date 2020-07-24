package com.currentbooking.profile.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.currentbooking.CurrentBookingApplication;
import com.currentbooking.R;
import com.currentbooking.utilits.CircularNetworkImageView;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.HttpsTrustManager;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.ProgressBarCircular;

import java.util.Objects;

public class ProfileFragment extends BaseFragment {

    // private ProfileViewModel mViewModel;

    OnProfileListener mListener;
    private TextView fName;
    private TextView lastName;
    private TextView mobileNo;
    private TextView dobField;
    private TextView address1;
    private TextView address2;
    private TextView pinCode;
    private TextView state;
    private CircularNetworkImageView ivProfileImageField;
    private ProgressBarCircular profileCircularBar;
    private AppCompatTextView maleField;
    private AppCompatTextView femaleField;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnProfileListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.my_profile));
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            String dob = myProfile.getDob();
            if (!TextUtils.isEmpty(dob)) {
                dobField.setText(DateUtilities.getCalendarFromMultipleDateFormats1(dob));
            }

            fName.setText(myProfile.getFirstName());
            lastName.setText(myProfile.getLastName());
            mobileNo.setText(myProfile.getMobileNumber());

            if (myProfile.getGender().equalsIgnoreCase(getString(R.string.male))) {
                selectedMale();
            } else if (myProfile.getGender().equalsIgnoreCase(getString(R.string.female))) {
                selectedFemale();
            }
            state.setText(myProfile.getState());
            address1.setText(myProfile.getAddress1());
            address2.setText(myProfile.getAddress2());
            pinCode.setText(myProfile.getPinCode());

            Bitmap bitmap = myProfile.getUserProfileImage().getValue();
            if (bitmap != null) {
                profileCircularBar.setVisibility(View.GONE);
                ivProfileImageField.setImageBitmap(bitmap);
            } else {
                String imageUrl = myProfile.getProfileImage();
                if (!TextUtils.isEmpty(imageUrl)) {
                    HttpsTrustManager.allowAllSSL();
                    ImageLoader imageLoader = CurrentBookingApplication.getInstance().getImageLoader();
                    imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            profileCircularBar.setVisibility(View.GONE);
                            Bitmap bitmap = response.getBitmap();
                            if (bitmap != null) {
                                ivProfileImageField.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            profileCircularBar.setVisibility(View.GONE);
                            ivProfileImageField.setImageResource(R.drawable.avatar);
                        }
                    });
                    ivProfileImageField.setImageUrl(imageUrl, imageLoader);
                } else {
                    profileCircularBar.setVisibility(View.GONE);
                }
            }
        }
    }

    private void selectedMale() {
        maleField.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg_selected));
        maleField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
        femaleField.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg));
        femaleField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent));
    }

    private void selectedFemale() {
        femaleField.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg_selected));
        femaleField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
        maleField.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg));
        maleField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        mobileNo = view.findViewById(R.id.mobile_no);
        AppCompatTextView email = view.findViewById(R.id.email);
        email.setText(MyProfile.getInstance().getEmail());
        dobField = view.findViewById(R.id.dob);
        address1 = view.findViewById(R.id.address1);
        address2 = view.findViewById(R.id.address2);
        state = view.findViewById(R.id.state);
        pinCode = view.findViewById(R.id.pin_code);
        view.findViewById(R.id.edit_profile).setOnClickListener(v -> {
            mListener.goToEditProfile();
        });
        ivProfileImageField = view.findViewById(R.id.iv_profile_image_field);
        profileCircularBar = view.findViewById(R.id.profile_circular_field);
        maleField = view.findViewById(R.id.male_field);
        femaleField = view.findViewById(R.id.female_field);
    }
}