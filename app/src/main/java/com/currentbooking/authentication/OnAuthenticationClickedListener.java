package com.currentbooking.authentication;

public interface OnAuthenticationClickedListener {
    void goToTicketBookingActivity();
    void goToForgotPassword();
    void validateOTP();

    void goToProfileActivity();
    boolean showPermission();
}
