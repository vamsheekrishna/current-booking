package com.currentbooking.utilits;

import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static boolean isValidMobile(String phone) {
        if ((phone != null && !phone.isEmpty()) && phone.length() == 10
                &&
                (Integer.parseInt(String.valueOf(phone.charAt(0))) > 5 && Integer.parseInt(String.valueOf(phone.charAt(0))) < 10)) {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }

    public static boolean internetConnectionAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(3000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (Exception e) {
            Log.d("Exception", "Exception: " + e.getMessage());
        }
        return inetAddress!=null && !inetAddress.equals("");
    }

    public static boolean isValidWord(String word) {
        if (word != null && word.length()>0) {
            return word.matches("^[a-zA-Z_]*$");
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getDateString(Long value) {
        return "";
    }

    public static int getIntegerValueFromString(String value) {
        int newValue = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                newValue = Integer.parseInt(value);
            } catch (Exception e) {

            }
        }
        return newValue;
    }

    public static double getDoubleValueFromString(String value) {
        double newValue = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                newValue = Double.parseDouble(value);
            } catch (Exception e) {

            }
        }
        return newValue;
    }
}
