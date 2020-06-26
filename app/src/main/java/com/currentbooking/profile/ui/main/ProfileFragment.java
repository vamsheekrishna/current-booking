package com.currentbooking.profile.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.utilits.CircleTransform;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    // private ProfileViewModel mViewModel;

    OnProfileListener mListener;
    private TextView fName;
    private TextView lastName;
    private TextView mobileNo;
    private TextView email;
    private TextView dob;
    private TextView address1;
    private TextView address2;
    private TextView state;
    private TextView pinCode;
    private AppCompatImageView ivProfileImageField;

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
        dob.setText(MyProfile.getInstance().getDob());
        address1.setText(MyProfile.getInstance().getAddress1());
        address2.setText(MyProfile.getInstance().getAddress2());
        pinCode.setText(MyProfile.getInstance().getPinCode());

        String imageUrl = MyProfile.getInstance().getProfileImage();
        if(!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.avatar).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.avatar).into(ivProfileImageField);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fName = ((TextView) view.findViewById(R.id.first_name));
        lastName = ((TextView) view.findViewById(R.id.last_name));
        mobileNo = ((TextView) view.findViewById(R.id.mobile_no));
        email = ((TextView) view.findViewById(R.id.email));
        email.setText(MyProfile.getInstance().getEmail());
        dob = (TextView)view.findViewById(R.id.dob);
        address1 = ((TextView) view.findViewById(R.id.address1));
        address2 = ((TextView) view.findViewById(R.id.address2));
        state = ((TextView) view.findViewById(R.id.state));
        state.setText(MyProfile.getInstance().getState());
        pinCode = ((TextView) view.findViewById(R.id.pin_code));
        view.findViewById(R.id.edit_profile).setOnClickListener(this);
        ivProfileImageField = view.findViewById(R.id.iv_profile_image_field);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_profile) {
            mListener.goToEditProfile();
        }
    }
}