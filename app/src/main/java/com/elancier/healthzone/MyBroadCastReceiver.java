package com.elancier.healthzone;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadCastReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    private final static String default_notification_channel_id = "default" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();


    }
}
