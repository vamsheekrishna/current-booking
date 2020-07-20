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

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.currentbooking.CurrentBookingApplication;
import com.currentbooking.R;
import com.currentbooking.utilits.CircularNetworkImageView;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.HttpsTrustManager;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.ProgressBarCircular;

import java.util.Calendar;
import java.util.Objects;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    // private ProfileViewModel mViewModel;

    OnProfileListener mListener;
    private TextView fName;
    private TextView lastName;
    private TextView mobileNo;
    private TextView dobField;
    private TextView address1;
    private TextView address2;
    private TextView pinCode;
    private CircularNetworkImageView ivProfileImageField;
    private ProgressBarCircular profileCircularBar;

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
        Objects.requireNonNull(getActivity()).setTitle("My Profile");
        fName.setText(MyProfile.getInstance().getFirstName());
        lastName.setText(MyProfile.getInstance().getLastName());
        mobileNo.setText(MyProfile.getInstance().getMobileNumber());
        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            String dob = myProfile.getDob();
            if (!TextUtils.isEmpty(dob)) {
                Calendar dobCalendar = DateUtilities.getCalendarFromDate2(dob);
                dobField.setText(DateUtilities.getDateOfBirthFromCalendar1(dobCalendar));
            }
        }

        address1.setText(MyProfile.getInstance().getAddress1());
        address2.setText(MyProfile.getInstance().getAddress2());
        pinCode.setText(MyProfile.getInstance().getPinCode());

        Bitmap bitmap = MyProfile.getInstance().getUserProfileImage().getValue();
        if (bitmap != null) {
            ivProfileImageField.setImageBitmap(bitmap);
        } else {
            if (myProfile != null) {
                String imageUrl = myProfile.getProfileImage();
                if (!TextUtils.isEmpty(imageUrl)) {
                    HttpsTrustManager.allowAllSSL();
                    ImageLoader imageLoader = CurrentBookingApplication.getInstance().getImageLoader();
                    imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            profileCircularBar.setVisibility(View.GONE);
                            Bitmap bitmap = response.getBitmap();
                            ivProfileImageField.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            profileCircularBar.setVisibility(View.GONE);
                            ivProfileImageField.setImageResource(R.drawable.avatar);
                        }
                    });
                    ivProfileImageField.setImageUrl(imageUrl, imageLoader);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        AppCompatTextView state = view.findViewById(R.id.state);
        state.setText(MyProfile.getInstance().getState());
        pinCode = view.findViewById(R.id.pin_code);
        view.findViewById(R.id.edit_profile).setOnClickListener(this);
        ivProfileImageField = view.findViewById(R.id.iv_profile_image_field);
        profileCircularBar = view.findViewById(R.id.profile_circular_field);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_profile) {
            mListener.goToEditProfile();
        }
    }
}