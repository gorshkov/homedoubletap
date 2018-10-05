package com.gmail.evgenygorshkov.homedoubletap;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

public class BroadcastReceiverService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_BOOT_COMPLETED.equals(action) ||
           Intent.ACTION_USER_PRESENT.equals(action)) {
                context.startService(new Intent(context, HomeButtonService.class));
        }
    }
}