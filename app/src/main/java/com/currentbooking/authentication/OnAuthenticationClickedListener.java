package com.currentbooking.authentication;

import com.currentbooking.ticketbooking.BaseListener;

public interface OnAuthenticationClickedListener {
    void goToHomeActivity();
    void goToForgotPassword();
    void validateOTP();
    void changePassword();
    void goToProfileActivity();
    boolean showPermission();
}
