package com.bear_vision.simplefirmata;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.hoho.android.usbserial.driver.*;
import com.hoho.android.usbserial.util.*;
import org.shokai.firmata.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public ArduinoFirmata mArduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArduino = new ArduinoFirmata(this);

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

        double tmpConversionFactor = 154.0/180.0;
        double tmpServoCommand = tmpAngle*tmpConversionFactor + 15;

        Log.d("MainActivity", "sendServoCommand(): " + String.valueOf(tmpServoCommand));
        mArduino.servoWrite(3, (int)tmpServoCommand ); //function input range: 15 -> 169  = 154 = 180 degree

    }

    public void sendLEDCommand(View arg_view) {
        // Do something in response to button
        ToggleButton tmpToggle = (ToggleButton)arg_view;
        String tmpText;

        if (tmpToggle.isChecked())
        {
            tmpText = "On";
        }
        else
        {
            tmpText = "Off";
        }

        Log.d("MainActivity", "sendLEDCommand(): " + tmpText );

        mArduino.digitalWrite(13, tmpToggle.isChecked() );

    }
}
