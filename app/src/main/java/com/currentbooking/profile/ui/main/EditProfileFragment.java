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
import com.currentbooking.home.HomeActivity;
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
    private AppCompatEditText etAddress;
    private AppCompatEditText etDistrict;
    private AppCompatEditText etState;
    private AppCompatEditText etPinCode;
    private AppCompatEditText email;
    private CircularNetworkImageView ivProfileImageField;
    private ProgressBarCircular profileCircularBar;

    private AppCompatTextView dobField;
    private AppCompatTextView maleField;
    private AppCompatTextView femaleField;
    private Calendar dateOfBirthCalendar;
    private String gender;
    private Bitmap profileImageBitmap;
    private String dateOfBirthValue;
    private boolean firstTimeUserLoggedIn = false;

    public static EditProfileFragment newInstance(boolean firstTimeUserLoggedIn) {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        Bundle extras = new Bundle();
        extras.putBoolean("firstTimeUserLoggedIn", firstTimeUserLoggedIn);
        editProfileFragment.setArguments(extras);
        return editProfileFragment;
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
        Bundle extras = getArguments();
        if (extras != null) {
            firstTimeUserLoggedIn = extras.getBoolean("firstTimeUserLoggedIn");
        }
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
        dobField = view.findViewById(R.id.dob);
        dobField.setOnClickListener(this);
        maleField= view.findViewById(R.id.male);
        maleField.setOnClickListener(this);
        femaleField = view.findViewById(R.id.female);
        femaleField.setOnClickListener(this);
        etAddress = view.findViewById(R.id.address1);
        etDistrict = view.findViewById(R.id.district);
        etState = view.findViewById(R.id.state);

        email = view.findViewById(R.id.email);
        etPinCode = view.findViewById(R.id.pin_code);
        view.findViewById(R.id.save_profile).setOnClickListener(this);
        ivProfileImageField = view.findViewById(R.id.iv_profile_image_field);
        ivProfileImageField.setOnClickListener(this);
        profileCircularBar = view.findViewById(R.id.profile_circular_field);

        MyProfile myProfile = MyProfile.getInstance();
        if (myProfile != null) {
            ((TextView) view.findViewById(R.id.mobile_no)).setText(myProfile.getMobileNumber());
            ((TextView) view.findViewById(R.id.email)).setText(myProfile.getEmail());
            etFirstName.setText(myProfile.getFirstName());
            etLastName.setText(myProfile.getLastName());
            etAddress.setText(myProfile.getAddress());
            etDistrict.setText(myProfile.getDistrict());
            etPinCode.setText(myProfile.getPinCode());
            email.setText(myProfile.getEmail());
            etState.setText(myProfile.getState());

            String dob = myProfile.getDob();
            if (!TextUtils.isEmpty(dob)) {
                dateOfBirthCalendar = DateUtilities.getCalendarFromMultipleDateFormats2(dob);
                dobField.setText(DateUtilities.getDateOfBirthFromCalendar1(dateOfBirthCalendar));
                dateOfBirthValue = DateUtilities.getDateOfBirthFromCalendar(dateOfBirthCalendar);
            }
            gender = myProfile.getGender();
            if(!TextUtils.isEmpty(gender)) {
                if (gender.equalsIgnoreCase(getString(R.string.male))) {
                    selectedMale();
                } else if (gender.equalsIgnoreCase(getString(R.string.female))) {
                    selectedFemale();
                }
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
                break;
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

    private void saveSelected() {

        String fName = Objects.requireNonNull(etFirstName.getText()).toString().trim();
        String lName = Objects.requireNonNull(etLastName.getText()).toString().trim();
        String _etAddress = Objects.requireNonNull(etAddress.getText()).toString().trim();
        String _etDistrict = Objects.requireNonNull(etDistrict.getText()).toString().trim();
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
        if (TextUtils.isEmpty(_etAddress)) {
            showDialog("", getString(R.string.error_address_one));
            return;
        }
        if (TextUtils.isEmpty(_etDistrict)) {
            showDialog("", getString(R.string.error_address_district));
            return;
        }
        if (TextUtils.isEmpty(_etState)) {
            showDialog("", getString(R.string.error_state));
            return;
        }
        /*if (profileImageBitmap == null) {
            showDialog("", getString(R.string.error_profile_image));
            return;
        }*/
        progressDialog.show();
        String encodedImage = getEncodedImage();
        LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        loginService.updateProfile(MyProfile.getInstance().getUserId(), fName, lName, gender, _email,
                _etAddress, _etDistrict, _etPinCode, dateOfBirthValue, encodedImage, _etState).enqueue(new Callback<ResponseUpdateProfile>() {
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
                                myProfile.setAddress(_etAddress);
                                myProfile.setDistrict(_etDistrict);
                                myProfile.setState(_etState);
                                myProfile.setPinCode(_etPinCode);
                                myProfile.setEmail(_email);
                                myProfile.setDob(dateOfBirthValue);
                                myProfile.setGender(gender);
                            }
                            showDialog("", response.body().getMsg(), pObject -> {
                                if (profileImageBitmap != null) {
                                    Bitmap bitmap = CommonUtils.getCircularBitmap(profileImageBitmap);
                                    MyProfile.getInstance().setUserProfileImage(bitmap);
                                }
                                String userName = String.format("%s %s", fName, lName);
                                MyProfile.getInstance().setUserNameDetails(userName);
                                MyProfile.getInstance().setUserEmailDetails(_email);
                                MyProfile.getInstance().setDateOfBirthDetails(dateOfBirthValue);
                                if(firstTimeUserLoggedIn) {
                                    gotoHomeScreen();
                                } else {
                                    Objects.requireNonNull(getActivity()).onBackPressed();
                                }
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

    private void gotoHomeScreen() {
        Intent intent = new Intent(requireActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }

    private void updateUserProfileImage() {
        Bitmap bitmap = MyProfile.getInstance().getUserProfileImage().getValue();
        if (bitmap != null) {
            profileCircularBar.setVisibility(View.GONE);
            ivProfileImageField.setImageBitmap(bitmap);
        } else {
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
            } else {
                profileImageBitmap = MyProfile.getInstance().getUserProfileImage().getValue();
                if (profileImageBitmap != null) {
                    ivProfileImageField.setImageBitmap(profileImageBitmap);
                }
                profileCircularBar.setVisibility(View.GONE);
            }
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