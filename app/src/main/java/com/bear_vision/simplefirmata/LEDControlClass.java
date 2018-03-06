package com.bear_vision.simplefirmata;

import android.os.Handler;
import android.util.Log;

import org.shokai.firmata.ArduinoFirmata;

/**
 * Created by BJES on 06-03-2018.
 */

public class LEDControlClass implements Runnable {
    public Handler mHandler = new Handler();
    private boolean mEnableBlink = false;
    private ArduinoFirmata mArduino;
    private int mLEDPin;
    private int mDeltaTime = 200; //[ms]
    private boolean mCurrentStateOn = false;


    //Constructor
    public LEDControlClass(ArduinoFirmata argArduino, int argLEDPin) {
        mArduino = argArduino;
        mLEDPin = argLEDPin;
    }

    @Override
    public void run() {
        if (mEnableBlink) {
            ToggleLED();
        }
        mHandler.postDelayed(this, mDeltaTime);
    }

    public void SetLED(boolean arg_value) {
        Log.d("LEDControlClass", "Turning LED: " + String.valueOf(arg_value) );
        mCurrentStateOn = arg_value;
        mArduino.digitalWrite(mLEDPin, arg_value);
    }

    public void ToggleLED() {
        mCurrentStateOn = !mCurrentStateOn;
        SetLED(mCurrentStateOn);
    }

    public void EnableBlink(boolean arg_value) {
        mEnableBlink = arg_value;
    }
}


