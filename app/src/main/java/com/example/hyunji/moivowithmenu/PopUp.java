package com.example.hyunji.moivowithmenu;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hyunji on 7/18/17.
 */

public class PopUp extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        Button turnOffAlarm = (Button) findViewById(R.id.turnOffAlarm);

        turnOffAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(PopUp.this, AlarmReceiver.class);
                myIntent.putExtra("onOff", "alarm off");

                //also put an extra long into the alarm off section to prevent crashes in a Null Pointer Exception
                myIntent.putExtra("soundChoice", "minion");

                //stop the ringtone
                sendBroadcast(myIntent);

//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PopUp.this);
//                Float latitude = prefs.getFloat("latitude", 0);
//                Float longitude = prefs.getFloat("longitude", 0);

                Intent weatherIntent = new Intent(PopUp.this, Weather.class);
                startActivity(weatherIntent);

                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.cancelAll();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("IsAlarmSet", false);
                editor.commit();
                Log.e("after popup", "Alarm is tured off");

            }
        });



    }

}
