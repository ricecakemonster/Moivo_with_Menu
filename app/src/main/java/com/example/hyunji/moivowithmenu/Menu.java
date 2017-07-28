package com.example.hyunji.moivowithmenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private TextView mTextMessage;
    private View setAlarmViewGroup;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.alarm:
//
//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//
//                    Boolean isAlarmSet = prefs.getBoolean("IsAlarmSet",false);
//                    Toast.makeText(Menu.this, "Is Alarm Set?" + isAlarmSet, Toast.LENGTH_SHORT).show();

                    Intent alarmIntent = new Intent(getBaseContext(), MainActivity.class);

                    startActivity(alarmIntent);
                    overridePendingTransition(0, 0);
                    return true;



                case R.id.weather:
                    Toast.makeText(Menu.this, "Weather button clicked", Toast.LENGTH_SHORT).show();
                    Intent locationIntent = new Intent(getBaseContext(), WeatherLocation.class);
                    locationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(locationIntent);
                    Intent weatherIntent = new Intent(getBaseContext(), Weather.class);
                    startActivity(weatherIntent);
                    overridePendingTransition(0, 0);
                    return true;
//
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("IsAlarmSet", false);
        editor.commit();
//        finish();
    }

}
