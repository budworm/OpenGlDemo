package com.opengl.demo;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import demo.opengl.com.opengldemo.R;

public class MainActivity extends AppCompatActivity {

    private MySurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉activity的标题，全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        mySurfaceView = findViewById(R.id.sv_surface);

        //mySurfaceView = new MySurfaceView(this);
        //setContentView(mySurfaceView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mySurfaceView != null) {
            mySurfaceView.onResume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mySurfaceView != null) {
            mySurfaceView.onPause();
        }
    }


    /**
     * 是否支持OpenGl
     * author zx
     * version 1.0
     * since 2018/4/2  .
     */
    private boolean checkSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;
        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));
        supportsEs2 = supportsEs2 || isEmulator;
        return supportsEs2;
    }


}
