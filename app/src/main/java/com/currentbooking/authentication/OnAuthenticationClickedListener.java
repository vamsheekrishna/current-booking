package com.currentbooking.authentication;

import android.content.Context;

import com.currentbooking.ticketbooking.BaseListener;

public interface OnAuthenticationClickedListener {
    void goToHomeActivity();
    void goToForgotPassword();
    void validateOTP();
    void changePassword();
    void goToProfileActivity();
    boolean showPermission();
    void goToLogout(Context context);
}
