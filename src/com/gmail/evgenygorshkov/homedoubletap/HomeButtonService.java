package com.gmail.evgenygorshkov.homedoubletap;

import android.app.Service;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.os.IBinder;
import android.provider.Settings;

public class HomeButtonService extends Service {
    private long lastTapTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showBlackActivity(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.gmail.evgenygorshkov.homedoubletap",
                                              "com.gmail.evgenygorshkov.homedoubletap.MainActivity"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra("reason");
                if (reason != null) {
                    if (reason.equals("homekey")) {
                        long tapTime = System.currentTimeMillis();
                        if ((tapTime - lastTapTime) < 400) {
                            showBlackActivity(context);
                        } else {
                            lastTapTime = tapTime;
                        }
                    }
                }
            }
        }
    };
}
