package com.gmail.evgenygorshkov.homedoubletap;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings.System;
import android.view.WindowManager;
import android.view.View;

public class MainActivity extends Activity {
    private int screenTimeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenTimeout = System.getInt(getContentResolver(), System.SCREEN_OFF_TIMEOUT, -1);
        System.putInt(getContentResolver(), System.SCREEN_OFF_TIMEOUT, 10);
        startService(new Intent(this, HomeButtonService.class));        
    }

    @Override
    protected void onDestroy() {
        System.putInt(getContentResolver(), System.SCREEN_OFF_TIMEOUT, screenTimeout);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().addFlags(WindowManager.LayoutParams.ANIMATION_CHANGED);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
