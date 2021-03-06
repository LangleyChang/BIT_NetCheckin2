package org.bitnp.netcheckin2.util;

import android.util.Log;

import org.bitnp.netcheckin2.network.HttpRequest;

/**
 * Created by langley on 3/17/15.
 */
public class ConnTest {
    private final static String TAG = "ConnTest";
    
    public static void test(final ConnTestCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpRequest.sendGet("http://www.baidu.com/img/bd_logo1.png", null, 1);
                Log.d(TAG, "Response is " + response);
                if(response.contains("PNG"))
                    callBack.onTestOver(true);
                else
                    callBack.onTestOver(false);
            }
        }).start();
    }
}