package com.android.websocialall.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class ConnectionChecker {
    public static boolean isConnected(Context context) {

        if (context!=null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netinfo = cm.getActiveNetworkInfo();
            if (netinfo != null && netinfo.isConnectedOrConnecting()) {
                NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
                        int downSpeed = nc.getLinkDownstreamBandwidthKbps();
                        int upSpeed = nc.getLinkUpstreamBandwidthKbps();
                        Log.d("INTERNET_SPEED", "isConnected: "+downSpeed+" "+upSpeed);
                        return true;
                    }
                }
                else
                    return false;
            } else
                return false;
        }
        else
            return false;
        return false;
    }
}
