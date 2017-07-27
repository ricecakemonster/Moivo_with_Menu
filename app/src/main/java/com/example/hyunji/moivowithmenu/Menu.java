package com.example.hyunji.moivowithmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    private TextView mTextMessage;
    private View setAlarmViewGroup;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.alarm:
//                    mTextMessage.setText(R.string.title_home);
                    setAlarmViewGroup = findViewById(R.id.settingAlarmView);

                    if (setAlarmViewGroup.getVisibility() == View.VISIBLE ){
                        Intent alarmIntent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(alarmIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    } else {

                    }


//                    Intent alarmIntent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(alarmIntent);
//                    overridePendingTransition(0, 0);
//                    return true;



                case R.id.weather:
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
    }

}
