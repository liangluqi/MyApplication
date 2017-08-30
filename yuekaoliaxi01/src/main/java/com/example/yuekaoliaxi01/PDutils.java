package com.example.yuekaoliaxi01;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hp on 2017/8/24.
 */

public class PDutils {
    public boolean PanDuan(Context context){
        ConnectivityManager cim= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cim.getActiveNetworkInfo();
        return  networkInfo!=null&&networkInfo.isConnected();
    }
}
