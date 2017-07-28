package com.example.hyunji.moivowithmenu;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class AlarmSet extends BaseActivity{

    TextView alarmset;
    Button cancelAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String alarmTime = prefs.getString("AlarmTime","");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("comingFromAlarmSet",true);

        Log.e("alarm set days", alarmTime);


        turnOffMessage1 = (TextView) findViewById(R.id.turnOffMessage1);
        turnOffMessage1.setText(alarmTime);
        turnOffMessage1.setTypeface(typefaceLorem);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }
}
