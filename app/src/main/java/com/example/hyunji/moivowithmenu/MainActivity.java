package com.example.hyunji.moivowithmenu;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends BaseActivity {
    private PendingIntent pendingIntent;
    private PendingIntent pendingIntentOnGoing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typefaceLorem = Typeface.createFromAsset(getAssets(), "fonts/Lorem.ttf");

        sunday = (TextView)findViewById(R.id.sunday);
        monday = (TextView)findViewById(R.id.monday);
        tuesday = (TextView)findViewById(R.id.tuesday);
        wednesday = (TextView)findViewById(R.id.wednesday);
        thursday = (TextView)findViewById(R.id.thursday);
        friday = (TextView)findViewById(R.id.friday);
        saturday = (TextView)findViewById(R.id.saturday);
        repeatButton = (TextView)findViewById(R.id.repeat);

        updateText = (TextView) findViewById(R.id.updateText);
//        turnOffMessage = (TextView) findViewById(R.id.turnOffMessage1);

        sunday.setTypeface(typefaceLorem);
        monday.setTypeface(typefaceLorem);
        tuesday.setTypeface(typefaceLorem);
        wednesday.setTypeface(typefaceLorem);
        thursday.setTypeface(typefaceLorem);
        friday.setTypeface(typefaceLorem);
        saturday.setTypeface(typefaceLorem);
        updateText.setTypeface(typefaceLorem);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void onAlarmOn(View view){
        myTimePicker = (TimePicker) findViewById(R.id.timePicker);

        myIntent.putExtra("onOff", "alarm on");

        myIntent.putExtra("soundChoice", selectedAlarmSound);

        if (Build.VERSION.SDK_INT >= 23) {
            myCal.set(Calendar.HOUR_OF_DAY, myTimePicker.getHour());
            myCal.set(Calendar.MINUTE, myTimePicker.getMinute());

        } else {
            myCal.set(Calendar.HOUR_OF_DAY, myTimePicker.getCurrentHour());
            myCal.set(Calendar.MINUTE, myTimePicker.getCurrentMinute());
        }


        String[] dayTextViewArray = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        for(int i=0; i<7; i++){
            String day = String.valueOf(dayTextViewArray[i]);
            int layoutID = getResources().getIdentifier(day, "id", getPackageName());

            TextView textView = (TextView) findViewById(layoutID);

            ColorDrawable colorDrawable = (ColorDrawable) textView.getBackground();
            int colorCode = colorDrawable.getColor();
            int dayNumber = 0;

            if (colorCode == Color.parseColor("#ffff8800")){
                switch (day) {
                    case "sunday":
                        dayNumber = 1;
                        break;
                    case "monday":
                        dayNumber = 2;
                        break;
                    case "tuesday":
                        dayNumber = 3;
                        break;
                    case "wednesday":
                        dayNumber = 4;
                        break;
                    case "thursday":
                        dayNumber = 5;
                        break;
                    case "friday":
                        dayNumber = 6;
                        break;
                    case "saturday":
                        dayNumber = 7;
                        break;

                }
                checkedDays.add(day);
                checkedDaysNum.add(dayNumber);
            }
        }

        // if the alarm is set for 7pm and it's already 7:01pm = > alarm is set for 7pm tomorrow.
        if (checkedDays.size() == 0){
            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myCal.getTimeInMillis(), pendingIntent);
            Log.e("Repeat?", "No repeating alarms");
        }

        //repeating alarms
        else {
            for (int i = 0; i < checkedDays.size(); i++) {
                if (checkedDays.size() == 1) {
                    myCal.set(Calendar.DAY_OF_WEEK, checkedDaysNum.get(i));
                    if(myCal.getTimeInMillis() < System.currentTimeMillis()) {
                        myCal.add(Calendar.DAY_OF_YEAR, 7);
                    }
                    calendars.add(myCal);
                } else if (checkedDays.size() > 1) {
                    calendarArray[i] = (Calendar) myCal.clone();
                    calendarArray[i].set(Calendar.DAY_OF_WEEK, checkedDaysNum.get(i));
                    if(calendarArray[i].getTimeInMillis() < System.currentTimeMillis()) {
                        calendarArray[i].add(Calendar.DAY_OF_YEAR, 7);
                    }
                    calendars.add(calendarArray[i]);
                }
            }


            for (int i = 0; i < calendars.size(); i++) {
                pendingIntent = PendingIntent.getBroadcast(this, i,
                        myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendars.get(i).getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                Log.e("Repeat Alarm?", "Repeating alarms");


            }
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean alarmSet = prefs.getBoolean("comingFromAlarmSet",false);
        Log.e("alarmSet", String.valueOf(alarmSet));

//        setContentView(R.layout.alarm_set);

//        turnOffMessage = (TextView) findViewById(R.id.turnOffMessage);
        Button cancelAlarm1 =(Button) findViewById(R.id.cancel1);
//        turnOffMessage.setTypeface(typefaceLorem);

        String alarmMessage = cancelAlarmText("Alarm set for ");
//        String alarmMessage = String.valueOf(turnOffMessage.getText());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("IsAlarmSet",true);
        editor.putBoolean("comingFromAlarmSet",false);
        editor.putString("AlarmTime", alarmMessage);

        Log.e("after Alarm set", "Alarm is set");

        editor.commit();

        Log.e("The sound id is", String.valueOf(selectedAlarmSound));

        Intent alarmOnIntent = new Intent(MainActivity.this, AlarmSet.class);
        startActivity(alarmOnIntent);
    }

}
