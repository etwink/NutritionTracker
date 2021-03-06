package com.example.nutritiontrackerguiv4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nutritiontrackerguiv4.Notifications.ReminderBroadcast;
import com.example.nutritiontrackerguiv4.database.NutritionDatabase;
import com.example.nutritiontrackerguiv4.database.Notifications;
import com.example.nutritiontrackerguiv4.Notifications.CreateNotification;

import java.util.Calendar;

public class NotificationSettingsActivity extends AppCompatActivity {

    private NutritionDatabase db;
    private String hour1;
    private String hour2;
    private String hour3;
    private String minute1;
    private String minute2;
    private String minute3;
    private boolean checker = false;
    String H1;
    String H2;
    String H3;
    String M1;
    String M2;
    String M3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        db = NutritionDatabase.getDatabase(getApplicationContext());

        try {

            Notifications load_nots = db.getNotificationsDAO().getAllNotifications().get(0);

            Integer h1 = load_nots.getHours_one();
            Integer h2 = load_nots.getHours_two();
            Integer h3 = load_nots.getHours_three();
            Integer m1 = load_nots.getMinutes_one();
            Integer m2 = load_nots.getMinutes_two();
            Integer m3 = load_nots.getMinutes_three();
            H1 = h1.toString();
            H2 = h2.toString();
            H3 = h3.toString();
            M1 = m1.toString();
            M2 = m2.toString();
            M3 = m3.toString();

            ((EditText) findViewById(R.id.editHour)).setText(H1);
            ((EditText) findViewById(R.id.editHour2)).setText(H2);
            ((EditText) findViewById(R.id.editHour3)).setText(H3);
            ((EditText) findViewById(R.id.editMinute)).setText(M1);
            ((EditText) findViewById(R.id.editMinute2)).setText(M2);
            ((EditText) findViewById(R.id.editMinute3)).setText(M3);


            if(!db.getNotificationsDAO().getAllNotifications().isEmpty()){
                checker = true;
            }else{
                checker = false;
            }

        } catch(Exception e) {
            if(!db.getNotificationsDAO().getAllNotifications().isEmpty()){
                checker = true;
            }else{
                checker = false;
            }
        }


        Button notificationEntry = (Button)findViewById(R.id.enterNotification);
        notificationEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hour1 = ((EditText)findViewById(R.id.editHour)).getText().toString(); // user input for hours
                    minute1 = ((EditText)findViewById(R.id.editMinute)).getText().toString(); // user input for minutes
                    Notifications newNot = db.getNotificationsDAO().getAllNotifications().get(0);
                    newNot.setHours_one(Integer.parseInt(hour1));
                    newNot.setMinutes_one(Integer.parseInt(minute1));
                    db.getNotificationsDAO().update(newNot);

                    if(!(hour1.equals("0") && minute1.equals("0"))){
                        makeNotification(hour1,minute1);
                    }


                } catch (Throwable e) {
                    CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Invalid Notification: Please re-enter your notification", 2000);
                }

                try {
                    hour2 = ((EditText)findViewById(R.id.editHour2)).getText().toString(); // user input for hours
                    minute2 = ((EditText)findViewById(R.id.editMinute2)).getText().toString(); // user input for minutes
                    Notifications newNot = db.getNotificationsDAO().getAllNotifications().get(0);
                    newNot.setHours_two(Integer.parseInt(hour2));
                    newNot.setMinutes_two(Integer.parseInt(minute2));
                    db.getNotificationsDAO().update(newNot);

                    if(!(hour2.equals("0") && minute2.equals("0"))){
                        makeNotification(hour2,minute2);
                    }


                } catch (Throwable e) {

                }

                try {
                    hour3 = ((EditText)findViewById(R.id.editHour3)).getText().toString(); // user input for hours
                    minute3 = ((EditText)findViewById(R.id.editMinute3)).getText().toString(); // user input for minutes
                    Notifications newNot = db.getNotificationsDAO().getAllNotifications().get(0);
                    newNot.setHours_three(Integer.parseInt(hour3));
                    newNot.setMinutes_three(Integer.parseInt(minute3));
                    db.getNotificationsDAO().update(newNot);

                    if(!(hour3.equals("0") && minute3.equals("0"))){
                        makeNotification(hour3,minute3);
                    }


                } catch (Throwable e) {

                }
                try{
                    if(hour1.isEmpty()){
                        hour1 = "0";
                    }
                    if(hour2.isEmpty()){
                        hour2 = "0";
                    }
                    if(hour3.isEmpty()){
                        hour3 = "0";
                    }
                    if(minute1.isEmpty()){
                        minute1 = "0";
                    }
                    if(minute2.isEmpty()){
                        minute2 = "0";
                    }
                    if(minute3.isEmpty()){
                        minute3 = "0";
                    }
                    Notifications notArr = new Notifications(Integer.parseInt(hour1),Integer.parseInt(hour2),Integer.parseInt(hour3),Integer.parseInt(minute1),Integer.parseInt(minute2),Integer.parseInt(minute3));
                    System.out.println(checker);
                    if(!checker){
                        db.getNotificationsDAO().insert(notArr);
                        System.out.println("i made it here");
                    }
                    else {
                        db.getNotificationsDAO().update(notArr);
                        System.out.println("i made it here2");
                    }

                }catch(NumberFormatException e){

                }


                Intent loadApp = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loadApp);
            }
        });
    }


    public void makeNotification(String hourIn, String minuteIn){

        int hours = Integer.parseInt(hourIn); // user input for hours
        int minutes = Integer.parseInt(minuteIn); // user input for minutes

        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Invalid Notification: Please re-enter your notification", 2000);
        } else {
            Calendar timeOff9 = Calendar.getInstance();
            int curHour = timeOff9.get(Calendar.HOUR_OF_DAY); // current hour of the day
            int curMin = timeOff9.get(Calendar.MINUTE); // current minute in the hour
            int milliInDay = 86400000;

            if (hours < curHour) {
                int hoursApart = 24 - (curHour - hours); // number of hours to wait
                int milliApart = (hoursApart * 3600) * 1000; // number of milliseconds to wait (for hours)

                if (minutes < curMin) {
                    int minApart = curMin - minutes; // how far apart are the minutes
                    int minMilliApart = (minApart * 60) * 1000; // find the minutes apart in milliseconds
                    int finalMilli = milliApart - minMilliApart; // find how long to delay
                    CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", finalMilli);

                } else if (minutes == curMin) {
                    CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", milliApart);
                } else  {
                    int minApart = minutes - curMin;
                    int minMilliApart = (minApart * 60) * 1000;
                    int finalMilli = milliApart + minMilliApart;
                    CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", finalMilli);
                }

            } else if (hours == curHour) {
                if (minutes < curMin) {
                    int minApart = curMin - minutes; // minutes apart from actual time
                    int milliApart = (minApart * 60) * 1000; // milliseconds apart from actual time
                    int finalMilli = milliInDay - milliApart; // milliseconds to wait
                    CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", finalMilli);

                } else if (minutes == curMin) {
                    CreateNotification.CreateNotificationWithDelay(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", 86400000);
                } else {
                    CreateNotification.CreateNotificationAtTime(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", hours,minutes,0);
                }

            } else {
                CreateNotification.CreateNotificationAtTime(getApplicationContext(), "Nutrition Tracker", "Reminder to enter Meal Data!", hours,minutes,0);
            }
        }



    }
}