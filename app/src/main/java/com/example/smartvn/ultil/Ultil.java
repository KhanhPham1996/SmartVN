package com.example.smartvn.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Ultil {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###");

    public static RequestOptions myOptions = new RequestOptions()
            .override(300, 300).diskCacheStrategy(DiskCacheStrategy.ALL);
    public static int PriceFomater(String Price){

        return Integer.valueOf(Pattern.compile("(\\,|\\VNĐ|\\s+)").matcher(Price).replaceAll(""));
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private Boolean checkNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)||   actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return  true;
        }else
            return false;
    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
