package com.pranav.movieclip.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pranav.movieclip.MovieClipGlobal;

/**
 * Created by kumar-5753 on 30/04/18.
 */

public class MovieClipUtil {
    public static boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) MovieClipGlobal.movieClipGlobalInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }
}
