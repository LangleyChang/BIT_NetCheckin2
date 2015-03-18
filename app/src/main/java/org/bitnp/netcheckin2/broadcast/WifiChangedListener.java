package org.bitnp.netcheckin2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import org.bitnp.netcheckin2.network.LoginHelper;
import org.bitnp.netcheckin2.service.LoginService;
import org.bitnp.netcheckin2.util.ConnTest;
import org.bitnp.netcheckin2.util.ConnTestCallBack;

public class WifiChangedListener extends BroadcastReceiver implements ConnTestCallBack {
    
    private final static String TAG = "WifiChangedListener";
    
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;

    public WifiChangedListener() {
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "Wifi status changed");
        
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(!mWifiManager.isWifiEnabled()) {
            return;
        }

        // turn on the wifi
        if(LoginService.isKeepAlive())
        {
            Intent service = new Intent(context, LoginService.class);
            service.setAction(LoginService.START_LISTEN);
            IBinder binder = peekService(context, service);
        }


        mWifiInfo = mWifiManager.getConnectionInfo();
        String currentSSID = mWifiInfo.getSSID();
        if(LoginHelper.isAutoLogin(currentSSID)){
            ConnTest.test(WifiChangedListener.this);
        }
        
    }

    @Override
    public void onTestOver(boolean result) {
        if(!result) {
            LoginHelper.asyncLogin();
            Log.d(TAG, "Try to login");
        }
        else
            Log.d(TAG, "Login already");
    }
}
