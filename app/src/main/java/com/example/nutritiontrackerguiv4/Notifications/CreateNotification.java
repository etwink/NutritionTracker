package com.example.nutritiontrackerguiv4.Notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.nutritiontrackerguiv4.GlobalVars;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class CreateNotification {

    public static String title = "";
    public static String body = "";

    public static void CreateNotificationWithDelay(Context context, String title, String body, int delayMillis){
        createNotificationChannel(context, title, body);

        CreateNotification.title = title;
        CreateNotification.body = body;

        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), GlobalVars.cID, intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                timeAtButtonClick + delayMillis,AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }

    public static void CreateNotificationAtTime(Context context, String title, String body, int HOUR_OF_DAY, int MINUTES, int SECONDS){
        createNotificationChannel(context, title, body);

        CreateNotification.title = title;
        CreateNotification.body = body;

        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), GlobalVars.cID, intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(ALARM_SERVICE);

        Calendar timeOff9 = Calendar.getInstance();
        timeOff9.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
        timeOff9.set(Calendar.MINUTE, MINUTES);
        timeOff9.set(Calendar.SECOND, SECONDS);

        //set that timer as a RTC Wakeup to alarm manager object
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeOff9.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    //This must be called before making a notification
    private static void createNotificationChannel(Context context, String title, String body){

        int sub = (int)(Math.random()*(1000-1+1)+1);

        GlobalVars.cID = (int)System.currentTimeMillis() + sub;

        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), GlobalVars.cID, intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(ALARM_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Integer.toString(GlobalVars.cID), name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }
}
