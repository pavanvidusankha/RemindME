package com.example.pavan.re_mindme.Reminder;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.pavan.re_mindme.R;

import static com.example.pavan.re_mindme.R.drawable.ic_alarm;
import static com.example.pavan.re_mindme.R.drawable.ic_android_black_24dp;

public class NotificationHelper extends ContextWrapper {
    public static final String textID="Alarm";
    public static final String textName="Alarm is Working";


    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            createChannels();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel =new NotificationChannel(textID,textName,NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel );
    }

    public NotificationManager getManager(){
        if(mManager==null){
            mManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String text){
        return new NotificationCompat.Builder(getApplicationContext(),textID)

       .setContentTitle("Reminder")
               .setContentText(text)
        .setSmallIcon(R.drawable.ic_alarm)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


    }


}
