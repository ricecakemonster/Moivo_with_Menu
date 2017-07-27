package com.example.hyunji.moivowithmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AlarmSet extends Menu{

    TextView alarmset;
    Button cancelAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set);
        getSupportActionBar().hide();

        alarmset = (TextView) findViewById(R.id.turnOffMessage1);
        cancelAlarm = (Button) findViewById(R.id.cancel1);



        cancelAlarm.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view){



            }
        });
    }



}
