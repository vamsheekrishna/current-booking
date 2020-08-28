package com.currentbooking.utilits;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Satya Seshu on 18/08/20.
 */
public class NetworkUtility {

    public static boolean isNetworkConnected(Context pContext) {
        ConnectivityManager cm = (ConnectivityManager) pContext	.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }
}
