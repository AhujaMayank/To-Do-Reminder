package com.example.admin.ui1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    static int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("My Notification")
                .setAutoCancel(true)
                .setContentTitle("Alarm !!!");
        Intent resultIntent=new Intent(context,ExpenseDetailActivity.class);
        resultIntent.putExtra("id",i);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(context,i,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0,mBuilder.build());
    }
}
