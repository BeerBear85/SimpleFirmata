package com.bear_vision.simplefirmata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import org.shokai.firmata.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public ArduinoFirmata mArduino;
    public ServoControlClass mServoControl;
    public int mServoPin = 3;
    public int mLedPin1 = 13;
    public int mLedPin2 = 11;
    Thread mServoThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArduino = new ArduinoFirmata(this);
        mServoControl = new ServoControlClass(mArduino, mServoPin);
        mServoThread = new Thread(mServoControl);
        mServoThread.start();

        try{
            mArduino.connect();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendServoCommand(View arg_view) {
        // Do something in response to button

        EditText tmpServoOrderEditText  = (EditText)findViewById(R.id.servo_order_number);
        String tmpServoOrderString      = tmpServoOrderEditText.getText().toString();

        Log.d("MainActivity", "sendServoCommand(): " + tmpServoOrderString);

        int tmpAngle = Integer.parseInt( tmpServoOrderString ); //degree
        mServoControl.setServoAngle(tmpAngle);
    }

    public void sendLED1Command(View arg_view) {
        // Do something in response to button
        ToggleButton tmpToggle = (ToggleButton)arg_view;
        Log.d("MainActivity", "sendLED1Command(): " + String.valueOf(tmpToggle.isChecked()) );

        mArduino.digitalWrite(mLedPin1, tmpToggle.isChecked() );
    }

    public void sendLED2Command(View arg_view) {
        // Do something in response to button
        ToggleButton tmpToggle = (ToggleButton)arg_view;
        Log.d("MainActivity", "sendLED2Command(): " + String.valueOf(tmpToggle.isChecked()) );

        mArduino.digitalWrite(mLedPin2, tmpToggle.isChecked() );
    }

    public void controlServoSweep(View arg_view) {
        // Do something in response to button
        ToggleButton tmpToggle = (ToggleButton)arg_view;
        Log.d("MainActivity", "controlServoSweep(): " + String.valueOf(tmpToggle.isChecked()) );

        if (tmpToggle.isChecked()){
            mServoControl.mEnableSweep = true;
        } else {
            mServoControl.mEnableSweep = false;
        }
    }
}
