package com.briskemen.unityadsdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.log.DeviceLog;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //private String gameId = "1481582";

    private String gameId = "1481589";

    private boolean isShowAds;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (UnityAds.isReady()) {
                        UnityAds.show(MainActivity.this);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final UnityAdsListener unityAdsListener = new UnityAdsListener();
        UnityAds.setListener(unityAdsListener);
        UnityAds.initialize(MainActivity.this, gameId, unityAdsListener);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UnityAds.isReady()){
                    UnityAds.show(MainActivity.this);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(final String zoneId) {

            DeviceLog.debug("onUnityAdsReady: " + zoneId);
            Log.i(TAG,"==========onUnityAdsReady==="+zoneId);
            toast("Ready", zoneId);
            if (!isShowAds) {
                isShowAds = true;
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onUnityAdsStart(String zoneId) {
            DeviceLog.debug("onUnityAdsStart: " + zoneId);
            toast("Start", zoneId);
            Log.i(TAG,"==========onUnityAdsStart==="+zoneId);
        }

        @Override
        public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {
            DeviceLog.debug("onUnityAdsFinish: " + zoneId + " - " + result);
            toast("Finish", zoneId + " " + result);
            Log.i(TAG,"==========onUnityAdsFinish==="+zoneId+"===result==="+result);
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            DeviceLog.debug("onUnityAdsError: " + error + " - " + message);
            toast("Error", error + " " + message);
            Log.i(TAG,"==========onUnityAdsError==="+error.toString()+"===message==="+message);
        }

        private void toast(String callback, String msg) {
            Toast.makeText(getApplicationContext(), callback + ": " + msg, Toast.LENGTH_SHORT).show();
        }
    }
}
