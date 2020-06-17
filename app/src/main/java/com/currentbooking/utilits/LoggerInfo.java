package com.currentbooking.utilits;

import android.util.Log;

import com.currentbooking.BuildConfig;

/**
 * Created by Satya Seshu on 17/06/20.
 */
public class LoggerInfo {

    public static void printLog(String from, Object message) {
        if (BuildConfig.DEBUG) {
            Log.d("current booking", String.format("%s : $%s", from, message));
        }
    }

    public static void errorLog(String from, Object message) {
        if (BuildConfig.DEBUG) {
            Log.d("current booking", String.format("%s : $%s", from, message));
        }
    }
}
