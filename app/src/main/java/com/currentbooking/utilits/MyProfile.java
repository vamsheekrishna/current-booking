package com.currentbooking.utilits;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.ProfileModel;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.encrypt.Encryption;
import com.currentbooking.utilits.encrypt.EncryptionFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.utilits.DateUtilities.CALENDAR_DATE_FORMAT_THREE;

public class MyProfile {
    private static MyProfile myProfile;

    private String userId;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String gender = "Male";
    private String mobileNumber;
    private String pinCode;
    private String profileImage;
    private String address1;
    private String address2;
    private String state;
    private MutableLiveData<Bitmap> userProfileImage = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TodayTickets.AvailableTickets>> todayTickets = new MutableLiveData<>();
    Encryption aesEncryption;

    private MyProfile() throws Exception {
        aesEncryption = EncryptionFactory.getEncryptionByName("AES");
        String input = "Rajan";
        String key = "QWRTEfnfdys635";//E-m!tr@2016
        aesEncryption.setKey(key);
    }
    public static MyProfile getInstance() {
        return myProfile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static MyProfile getInstance(ProfileModel profileModel) throws Exception {
        if(null == myProfile) {
            myProfile = new MyProfile();
            myProfile.setData(profileModel);
        }
        return myProfile;
    }

    private void setData(ProfileModel profileModel) {
        // userId = getDecryptedString(profileModel.getUserID());
        userId = profileModel.getUserID();
        firstName = profileModel.getFirstName();
        lastName = profileModel.getLastName();
        dob = profileModel.getDob();
        // email = getDecryptedString(profileModel.getEmail());
        // mobileNumber = getDecryptedString(profileModel.getMobileNumber());
        email = profileModel.getEmail();
        mobileNumber = profileModel.getMobileNumber();
        pinCode = profileModel.getPinCode();
        profileImage = profileModel.getProfileImage();
        address1 = profileModel.getAddress();
        address2 = profileModel.getAddress2();
        state = profileModel.getState();

    }

    public MutableLiveData<Bitmap> getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Bitmap userImageBitmap) {
        userProfileImage.setValue(userImageBitmap);
    }

    public MutableLiveData<ArrayList<TodayTickets.AvailableTickets>> getTodayTickets() {
        return todayTickets;
    }

    public void setTodayTickets(ArrayList<TodayTickets.AvailableTickets> todayTickets) {
        this.todayTickets.setValue(todayTickets);
    }

    public String getEncryptedString(String input) {
        String data ="";
        try {
            data = aesEncryption.encrypt(input);
            Log.d("encryption", input+" -> "+data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getDecryptedString(String input) {
        String data ="";
        try {
            data = aesEncryption.decrypt(input);
            Log.d("encryption", input+" -> "+data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void getAvailableLiveTickets() {
        String date = DateUtilities.getTodayDateString(CALENDAR_DATE_FORMAT_THREE);
        String id = MyProfile.getInstance().getUserId();
        TicketBookingServices service = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        service.getTodayTicket(date, id).enqueue(new Callback<TodayTickets>() {
            @Override
            public void onResponse(@NotNull Call<TodayTickets> call, @NotNull Response<TodayTickets> response) {
                TodayTickets todayTickets = response.body();
                if (todayTickets != null) {
                    if (todayTickets.getStatus().equalsIgnoreCase("success")) {
                        ArrayList<TodayTickets.AvailableTickets> data = todayTickets.getAvailableTickets();
                        if (null != data && data.size() > 0) {
                            MyProfile.getInstance().setTodayTickets(data);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<TodayTickets> call, @NotNull Throwable t) {
                // showDialog("onFailure", "" + t.getMessage());
                LoggerInfo.errorLog("getAvailableLiveTickets OnFailure", t.getMessage());
            }
        });
    }
}
