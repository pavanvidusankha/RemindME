package com.example.pavan.re_mindme.Countdown;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.pavan.re_mindme.Reminder.AlarmActivity;
import com.example.pavan.re_mindme.Countdown.NotificationHelper;

public class AlertReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent){



        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1,nb.build());

    }

}
