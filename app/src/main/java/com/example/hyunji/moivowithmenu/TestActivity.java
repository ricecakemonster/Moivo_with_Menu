package com.example.hyunji.moivowithmenu;


 import android.graphics.Color;
         import android.os.Bundle;
         import android.support.design.widget.FloatingActionButton;
         import android.support.design.widget.Snackbar;
         import android.support.v7.app.AppCompatActivity;
         import android.support.v7.widget.Toolbar;
         import android.view.View;
         import android.widget.LinearLayout;
         import android.widget.RadioButton;
         import android.widget.RadioGroup;
 import android.widget.Toast;

public class TestActivity extends Menu {
    LinearLayout dynamicContent, bottonNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();

    }
}