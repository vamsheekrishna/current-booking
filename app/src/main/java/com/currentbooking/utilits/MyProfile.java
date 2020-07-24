package com.currentbooking.utilits;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.ProfileModel;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.encrypt.Encryption;
import com.currentbooking.utilits.encrypt.EncryptionFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

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
    private MutableLiveData<String> userNameDetails = new MutableLiveData<>("");
    private MutableLiveData<String> userEmailDetails = new MutableLiveData<>("");
    private MutableLiveData<String> dateOfBirthDetails = new MutableLiveData<>("");
    private MutableLiveData<ArrayList<MyTicketInfo>> todayTickets = new MutableLiveData<>();
    Encryption aesEncryption;
    private MutableLiveData<Integer> currentBookingTicketCount = new MutableLiveData<>(0);

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

    public MutableLiveData<String> getDateOfBirthDetails() {
        return dateOfBirthDetails;
    }

    public void setDateOfBirthDetails(String dateOfBirth) {
        this.dateOfBirthDetails.setValue(dateOfBirth);
    }

    public MutableLiveData<String> getUserNameDetails() {
        return userNameDetails;
    }

    public void setUserNameDetails(String userName) {
        this.userNameDetails.setValue(userName);
    }

    public MutableLiveData<String> getUserEmailDetails() {
        return userEmailDetails;
    }

    public void setUserEmailDetails(String email) {
        this.userEmailDetails.setValue(email);
    }

    public MutableLiveData<Bitmap> getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Bitmap userImageBitmap) {
        userProfileImage.setValue(userImageBitmap);
    }

    public MutableLiveData<ArrayList<MyTicketInfo>> getTodayTickets() {
        return todayTickets;
    }

    public void setTodayTickets(ArrayList<MyTicketInfo> todayTickets) {
        try {
            Objects.requireNonNull(this.todayTickets.getValue()).clear();
        } catch (Exception e) {

        }
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

    public void updateLiveTickets(Dialog progressDialog) {
        String date = DateUtilities.getTodayDateString(CALENDAR_DATE_FORMAT_THREE);
        String id = MyProfile.getInstance().getUserId();
        TicketBookingServices service = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
        progressDialog.show();
        service.getCurrentBookingTicket(date, id).enqueue(new Callback<TodayTickets>() {
            @Override
            public void onResponse(@NotNull Call<TodayTickets> call, @NotNull Response<TodayTickets> response) {
                TodayTickets todayTickets = response.body();
                if (todayTickets != null) {
                    if (todayTickets.getStatus().equalsIgnoreCase("success")) {
                        ArrayList<MyTicketInfo> data = todayTickets.getAvailableTickets();
                        if (null != data && data.size() > 0) {
                            MyProfile.getInstance().setTodayTickets(data);
                            Integer size  = (Integer)data.size();
                            getCurrentBookingTicketCount().setValue(size);
                        }
                    }
                }
                progressDialog.cancel();
            }

            @Override
            public void onFailure(@NotNull Call<TodayTickets> call, @NotNull Throwable t) {
                // showDialog("onFailure", "" + t.getMessage());
                LoggerInfo.errorLog("getAvailableLiveTickets OnFailure", t.getMessage());
                progressDialog.cancel();
            }
        });
    }

    public MutableLiveData<Integer> getCurrentBookingTicketCount() {
        return currentBookingTicketCount;
    }

    public void setCurrentBookingTicketCount(MutableLiveData<Integer> currentBookingTicketCount) {
        this.currentBookingTicketCount = currentBookingTicketCount;
    }

    public void resetMyProfile() {
        myProfile = null;
    }
}
