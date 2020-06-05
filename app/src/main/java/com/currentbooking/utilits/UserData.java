package com.currentbooking.utilits;

public class UserData {
    private static UserData userData = null;
    private UserData() {

    }
    public static UserData getInstance() {
        return userData;
    }
}
