package com.bear_vision.simplefirmata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendServoCommand(View arg_view) {
        // Do something in response to button
        Log.d("MainActivity", "sendServoCommand()");
    }

    public void sendLEDCommand(View arg_view) {
        // Do something in response to button
        Log.d("MainActivity", "sendLEDCommand()");
    }
}
