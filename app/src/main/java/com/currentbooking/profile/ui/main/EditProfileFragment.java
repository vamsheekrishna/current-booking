package com.currentbooking.profile.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.currentbooking.CurrentBookingApplication;
import com.currentbooking.R;
import com.currentbooking.interfaces.DateTimeInterface;
import com.currentbooking.utilits.CircularNetworkImageView;
import com.currentbooking.utilits.CommonUtils;
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.HttpsTrustManager;
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.responses.ResponseUpdateProfile;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.utilits.views.ProgressBarCircular;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatEditText etFirstName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etAddress1;
    private AppCompatEditText etAddress2;
    private AppCompatEditText etState;
    private AppCompatEditText etPinCode;
    private AppCompatEditText email;
    private CircularNetworkImageView ivProfileImageField;
    private ProgressBarCircular profileCircularBar;

    AppCompatTextView dobField, male, female;
    private Calendar dateOfBirthCalendar;
    String gender = "Male";
    private Bitmap profileImageBitmap;
    private String dateOfBirthValue;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dateOfBirthCalendar = Calendar.getInstance();
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Edit Profile");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etFirstName = view.findViewById(R.id.first_name);
        etLastName = view.findViewById(R.id.last_name);
        etFirstName.setText(MyProfile.getInstance().getFirstName());
        etLastName.setText(MyProfile.getInstance().getLastName());
        ((TextView) view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView) view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        dobField = view.findViewById(R.id.dob);
        //dob.setText(MyProfile.getInstance().getDob());
        dobField.setOnClickListener(this);
        male= view.findViewById(R.id.male);
        male.setOnClickListener(this);
        female = view.findViewById(R.id.female);
        female.setOnClickListener(this);
        etAddress1 = view.findViewById(R.id.address1);
        etAddress1.setText(MyProfile.getInstance().getAddress1());
        etAddress2 = view.findViewById(R.id.address2);
        etAddress2.setText(MyProfile.getInstance().getAddress2());
        etState = view.findViewById(R.id.state);
        etState.setText(MyProfile.getInstance().getState());
        email = view.findViewById(R.id.email);
        email.setText(MyProfile.getInstance().getEmail());
        etPinCode = view.findViewById(R.id.pin_code);
        etPinCode.setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.save_profile).setOnClickListener(this);
        ivProfileImageField = view.findViewById(R.id.iv_profile_image_field);
        ivProfileImageField.setOnClickListener(this);
        profileCircularBar = view.findViewById(R.id.profile_circular_field);

        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            String dob = myProfile.getDob();
            if (!TextUtils.isEmpty(dob)) {
                dateOfBirthCalendar = DateUtilities.getCalendarFromMultipleDateFormats2(dob);
                dobField.setText(DateUtilities.getDateOfBirthFromCalendar1(dateOfBirthCalendar));
                dateOfBirthValue = DateUtilities.getDateOfBirthFromCalendar(dateOfBirthCalendar);
            }
            if (myProfile.getGender().equalsIgnoreCase(getString(R.string.male))) {
                selectedMale();
            } else {
                selectedFemale();
            }

            updateUserProfileImage();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_profile:
                saveSelected();
                break;
            case R.id.dob:
                dateOfPickerSelected();
            case R.id.female:
                gender = getString(R.string.female);
                selectedFemale();
                break;
            case R.id.male:
                gender = getString(R.string.male);
                selectedMale();
                break;
            case R.id.iv_profile_image_field:
                profileSelected();
                break;
            default:
                break;
        }
    }

    private void profileSelected() {
        CropImage.activity()
                .start(Objects.requireNonNull(getContext()), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri profileImageUri = result.getUri();
                        if (profileImageUri != null) {
                            InputStream imageStream = requireActivity().getContentResolver().openInputStream(profileImageUri);
                            profileImageBitmap = BitmapFactory.decodeStream(imageStream);
                            if (profileImageBitmap != null) {
                                ivProfileImageField.setImageBitmap(profileImageBitmap);
                            }
                        }
                    } catch (Exception ex) {
                        showDialog("", ex.getMessage());
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    LoggerInfo.errorLog("cropping error", error.getMessage());
                }
            }
        }
    }

    private void selectedMale() {
        male.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg_selected));
        male.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
        female.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg));
        female.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent));
    }

    private void selectedFemale() {
        female.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg_selected));
        female.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
        male.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.gender_bg));
        male.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent));
    }

    private void saveSelected() {

        String fName = Objects.requireNonNull(etFirstName.getText()).toString().trim();
        String lName = Objects.requireNonNull(etLastName.getText()).toString().trim();
        String _etAddress1 = Objects.requireNonNull(etAddress1.getText()).toString().trim();
        String _etAddress2 = Objects.requireNonNull(etAddress2.getText()).toString().trim();
        String _etState =  Objects.requireNonNull(etState.getText()).toString().trim();
        String _etPinCode = Objects.requireNonNull(etPinCode.getText()).toString().trim();
        String _email = Objects.requireNonNull(email.getText()).toString().trim();

        if(TextUtils.isEmpty(fName)) {
            showDialog("", getString(R.string.error_first_name));
            return;
        }
        if (TextUtils.isEmpty(lName)) {
            showDialog("", getString(R.string.error_last_name));
            return;
        }
        if (TextUtils.isEmpty(_etAddress1)) {
            showDialog("", getString(R.string.error_address_one));
            return;
        }
        if (TextUtils.isEmpty(_etAddress2)) {
            showDialog("", getString(R.string.error_address_two));
            return;
        }
        if (TextUtils.isEmpty(_etState)) {
            showDialog("", getString(R.string.error_state));
            return;
        }
        progressDialog.show();
        String encodedImage = getEncodedImage();
        LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        loginService.updateProfile(MyProfile.getInstance().getUserId(), fName, lName, gender, _email,
                _etAddress1, _etAddress2, _etPinCode, dateOfBirthValue, encodedImage, _etState).enqueue(new Callback<ResponseUpdateProfile>() {
            @Override
            public void onResponse(@NotNull Call<ResponseUpdateProfile> call, @NotNull Response<ResponseUpdateProfile> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            MyProfile myProfile = MyProfile.getInstance();
                            if (myProfile != null) {
                                myProfile.setFirstName(fName);
                                myProfile.setLastName(lName);
                                myProfile.setAddress1(_etAddress1);
                                myProfile.setAddress2(_etAddress2);
                                myProfile.setState(_etState);
                                myProfile.setPinCode(_etPinCode);
                                myProfile.setEmail(_email);
                                myProfile.setDob(dateOfBirthValue);
                                myProfile.setGender(gender);
                            }
                            showDialog("", response.body().getMsg(), pObject -> {
                                Bitmap bitmap = CommonUtils.getCircularBitmap(profileImageBitmap);
                                MyProfile.getInstance().setUserProfileImage(bitmap);
                                String userName = String.format("%s %s", fName, lName);
                                MyProfile.getInstance().setUserNameDetails(userName);
                                MyProfile.getInstance().setUserEmailDetails(_email);

                                Objects.requireNonNull(getActivity()).onBackPressed();
                            });
                        } else {
                            showDialog("", response.body().getMsg());
                        }
                    }  else {
                        showDialog("", getString(R.string.no_information_available));
                    }
                } else {
                    showDialog("", getString(R.string.no_information_available));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseUpdateProfile> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                showDialog("", t.getMessage());
            }
        });
    }

    private void updateUserProfileImage() {
        String imageUrl = MyProfile.getInstance().getProfileImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            HttpsTrustManager.allowAllSSL();
            ImageLoader imageLoader = CurrentBookingApplication.getInstance().getImageLoader();
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    profileCircularBar.setVisibility(View.GONE);
                    profileImageBitmap = response.getBitmap();
                    ivProfileImageField.setImageBitmap(profileImageBitmap);
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

    private String getEncodedImage() {
        try {
            if (profileImageBitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                profileImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                return Base64.encodeToString(b, Base64.DEFAULT);
            }
        } catch (Exception ex) {
            LoggerInfo.errorLog("encode image exception", ex.getMessage());
        }
        return "";
    }

    private void dateOfPickerSelected() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle extras = new Bundle();
        extras.putInt("Year", dateOfBirthCalendar.get(Calendar.YEAR));
        extras.putInt("Month", dateOfBirthCalendar.get(Calendar.MONTH));
        extras.putInt("DayOfMonth", dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerFragment.setArguments(extras);
        datePickerFragment.setCallBack(new DateTimeInterface() {
            @Override
            public void onTimeSet(Integer hourOfDay, Integer minute, String amOrPm) {

            }

            @Override
            public void onDateSet(Integer year, Integer month, Integer dayOfMonth) {
                dateOfBirthCalendar.set(Calendar.YEAR, year);
                dateOfBirthCalendar.set(Calendar.MONTH, month);
                dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dobField.setText(DateUtilities.getDateOfBirthFromCalendar1(dateOfBirthCalendar));
                dateOfBirthValue = DateUtilities.getDateOfBirthFromCalendar(dateOfBirthCalendar);
            }
        });
        if (!Objects.requireNonNull(getActivity()).isFinishing()) {
            datePickerFragment.show(getActivity().getSupportFragmentManager(), DatePickerFragment.class.getName());
        }
    }
}