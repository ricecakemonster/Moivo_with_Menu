package com.example.hyunji.moivowithmenu;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class BaseActivity extends Activity {

    LinearLayout dynamicContent, bottonNavBar;



    AlarmManager alarmManager;
    TextView updateText;
    TextView turnOffMessage;
    TextView  turnOffMessage1;
    LinearLayout settingAlarmView;
    RelativeLayout cancelAlarmView;

    private boolean viewGroupIsVisible = true;

    private View setAlarmViewGroup;
    private View cancelAlarmViewGroup;

    Context context;
    private PendingIntent pendingIntent;
    private PendingIntent pendingIntentOnGoing;
    private Spinner spinner;
    TimePicker myTimePicker;
    int selectedAlarmSound;
    int hour;
    int minute;
    Calendar myCal, myCal1, myCal2, myCal3, myCal4, myCal5, myCal6;

    Typeface typefaceLorem;
    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    TextView repeatButton;
    final ArrayList<String> checkedDays = new ArrayList<>();
    final ArrayList<Integer> checkedDaysNum = new ArrayList<>();
    final ArrayList<Calendar> calendars = new ArrayList<>();
    Calendar[] calendarArray = {myCal, myCal1, myCal2, myCal3, myCal4, myCal5, myCal6};
    Button cancelAlarm1;
    Intent myIntent;

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.alarm:
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                    Boolean isAlarmSet = prefs.getBoolean("IsAlarmSet",false);


//                    if (isAlarmSet == false){
//                        Toast.makeText(BaseActivity.this, "Alarm is not set", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(BaseActivity.this, "Alarm is already set", Toast.LENGTH_SHORT).show();
//
//                    }

                    return true;



                case R.id.weather:
                    Intent locationIntent = new Intent(getBaseContext(), WeatherLocation.class);
                    locationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(locationIntent);
                    Intent weatherIntent = new Intent(getBaseContext(), Weather.class);
                    startActivity(weatherIntent);
                    overridePendingTransition(0, 0);
                    return true;

            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAlarmViewGroup = findViewById(R.id.settingAlarmView);
        cancelAlarmViewGroup = findViewById(R.id.cancelAlarmView);



        this.context = this;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        Boolean isItSet = prefs.getBoolean("IsAlarmSet",false);

        Log.e("onCreate", "here");
        Log.e("isAlarmSet????", String.valueOf(isItSet));


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
        turnOffMessage = (TextView) findViewById(R.id.turnOffMessage1);

        sunday.setTypeface(typefaceLorem);
        monday.setTypeface(typefaceLorem);
        tuesday.setTypeface(typefaceLorem);
        wednesday.setTypeface(typefaceLorem);
        thursday.setTypeface(typefaceLorem);
        friday.setTypeface(typefaceLorem);
        saturday.setTypeface(typefaceLorem);
        updateText.setTypeface(typefaceLorem);

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAlarmSound = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });



        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        myCal = Calendar.getInstance();


        myIntent = new Intent(this, AlarmReceiver.class);

        final Intent changeViewIntent = new Intent(this, PopUp.class);


        final Button alarmOn = (Button) findViewById(R.id.alarmOn);
        final Button cancelAlarm1 = (Button) findViewById(R.id.cancel1);
        final TextView alarmSetupTextView = (TextView) findViewById(R.id.alarmSetupTextView);
        final TextView turnOffMessage = (TextView) findViewById(R.id.turnOffMessage1);


    }


    public void onCancel(View view) {
        Log.e("onCancel", "working in on Cancel");
        Toast.makeText(context, "Alarm is Canceled!", Toast.LENGTH_SHORT).show();

        Intent cancelIntent = new Intent(BaseActivity.this, MainActivity.class);



        pendingIntent = PendingIntent.getBroadcast(this, 1,
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
        }

        // cancel alarms
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("IsAlarmSet", false);
        Log.e("after Alarm set", "Alarm is canceled");

        editor.commit();
        resetAlarmText("Alarm Canceled for ");
        updateText.setText("Alarm Canceled.\nSet New Alarm");

        checkedDays.clear();
        Log.e("checkedDays after clear", String.valueOf(checkedDays));


        startActivity(cancelIntent);
    }

    public void setReapeatAlarmClick(View view) {

        TextView selectDayView = (TextView) view;
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        int colorCode = colorDrawable.getColor();
        if (colorCode == Color.parseColor("#00000000")) {
            view.setBackgroundColor(Color.parseColor("#ffff8800"));
        } else {
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }



    }

    public void resetAlarmText(String s) {
        String string = showTime(s);
        updateText.setText(string);
    }

    public String cancelAlarmText(String s) {
        String string  = showTime(s);
        return string;
    }

    private String showTime(String s){

        hour = myCal.get(Calendar.HOUR_OF_DAY);
        minute = myCal.get(Calendar.MINUTE);

        String hourString = String.valueOf(hour);
        String minuteString = String.valueOf(minute);

        String amPm = "am";
        // convert 24 hour time to 12 hour time
        if (hour > 12) {
            hourString = String.valueOf(hour - 12);
            amPm = "pm";
        }

        if (minute < 10) {
            minuteString = "0" + String.valueOf(minute);
        }
        String days = "";
        for(int i=0; i<checkedDays.size(); i++){
            String day = checkedDays.get(i);
            days += Character.toUpperCase(day.charAt(0)) + day.substring(1) + " ";

        }

//        if myCal.get(Calendar.DAY_OF_WEEK)
        String  string = s + hourString + ":" + minuteString + amPm + "\n" + days;
//        Log.e("checkedDays", String.valueOf(checkedDays));
        return string;

    }
}
