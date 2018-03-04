package com.bear_vision.simplefirmata;

/**
 * Created by BJES on 04-03-2018.
 */

import android.os.Handler;
import android.util.Log;
import org.shokai.firmata.*;

public class ServoControlClass implements Runnable {
    public Handler mHandler = new Handler();
    public boolean mEnableSweep = false;


    //Servo/Arduino HW config
    private double mServoMaxAngle = 180.0;
    private double mMaxCommand = 169.0;
    private double mMinCommand = 15.0;
    private double mConversionFactor = (mMaxCommand - mMinCommand)/mServoMaxAngle;
    private ArduinoFirmata mArduino;
    private int mServoPin;

    // Sweep parameters
    private int mSweepAngle = 0; //Start angle
    private int mSweepAngleDelta = 5; //Setp size
    private int mSweepDeltaTime = 200; //[ms]
    private boolean mPositiveSweepDirection = true; //Start direction


    //Constructor
    public ServoControlClass(ArduinoFirmata argArduino, int argServoPin)
    {
        mArduino = argArduino;
        mServoPin = argServoPin;
    }

    @Override
    public void run() {
        if (mEnableSweep) {
            Log.d("ServoControlClass", "running sweep()" );
            if (mPositiveSweepDirection) {
                mSweepAngle += mSweepAngleDelta;
            } else {
                mSweepAngle -= mSweepAngleDelta;
            }
            if (mSweepAngle >= mServoMaxAngle) {
                mSweepAngle = (int) mServoMaxAngle;
                mPositiveSweepDirection = false;
            } else if (mSweepAngle <= 0) {
                mSweepAngle = 0;
                mPositiveSweepDirection = true;
            }
            setServoAngle(mSweepAngle);
        }
        mHandler.postDelayed(this, mSweepDeltaTime);
    }



    public void setServoAngle(int arg_angle) {

        Log.d("ServoControlClass", "setServoAngle angle: " + String.valueOf(arg_angle) );
        if(arg_angle > mServoMaxAngle)
            arg_angle = (int)mServoMaxAngle;

        double tmpServoCommand = arg_angle*mConversionFactor + mMinCommand;
        mArduino.servoWrite(mServoPin, (int)tmpServoCommand ); //function input range: 15 -> 169  = 154 = 180 degree
    }

}
