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

    private double mCurrentAngle = 0; //Start angle


    //Servo/Arduino HW config
    private double mServoMinAngle = 0.0;
    private double mServoMaxAngle = 180.0;
    private double mMaxCommand = 169.0;
    private double mMinCommand = 15.0;
    private double mConversionFactor = (mMaxCommand - mMinCommand)/mServoMaxAngle;
    private ArduinoFirmata mArduino;
    private int mServoPin;

    // Sweep parameters
    private double mSweepSpeed = 50; //deg/s
    private int mSweepDeltaTime = 100; //[ms]
    private boolean mPositiveSweepDirection = true; //Start direction
    private double mSweepMinAngle = 0;
    private double mSweepMaxAngle = mServoMaxAngle;
    private double mSweepAngleDelta = mSweepSpeed * 0.001 * (double)mSweepDeltaTime; //Step size


    //Constructor
    public ServoControlClass(ArduinoFirmata argArduino, int argServoPin)
    {
        mArduino = argArduino;
        mServoPin = argServoPin;
    }

    @Override
    public void run() {
        if (mEnableSweep) {
            iterateSweep();
        }
        mHandler.postDelayed(this, mSweepDeltaTime);
    }

    private void iterateSweep(){
        Log.d("ServoControlClass", "iterating sweep" );
        if (mPositiveSweepDirection) {
            mCurrentAngle += mSweepAngleDelta;
        } else {
            mCurrentAngle -= mSweepAngleDelta;
        }
        if (mCurrentAngle >= mSweepMaxAngle) {
            mCurrentAngle = mSweepMaxAngle;
            mPositiveSweepDirection = false;
        } else if (mCurrentAngle <= mSweepMinAngle) {
            mCurrentAngle = mSweepMinAngle;
            mPositiveSweepDirection = true;
        }
        setServoAngle(mCurrentAngle);
    }

    public void setSweepSpeed(double arg_sweep_speed) {
        Log.d("ServoControlClass", "Changed sweep speed to: " + String.valueOf(arg_sweep_speed));
        mSweepSpeed = arg_sweep_speed; //deg/s
        mSweepAngleDelta = mSweepSpeed * (0.001 * (double)mSweepDeltaTime); //Step size
    }

    public void setSweepRange(double argMinAngle, double argMaxAngle)
    {
        mSweepMinAngle = argMinAngle;
        mSweepMaxAngle = argMaxAngle;
    }


    public void setServoAngle(double arg_angle) {
        Log.d("ServoControlClass", "setServoAngle angle: " + String.valueOf(arg_angle) );
        if(arg_angle > mServoMaxAngle)
            arg_angle = mServoMaxAngle;
        else if (arg_angle < mServoMinAngle)
            arg_angle = mServoMinAngle;

        mCurrentAngle = arg_angle;

        double tmpServoCommand = arg_angle*mConversionFactor + mMinCommand;
        mArduino.servoWrite(mServoPin, (int)tmpServoCommand ); //function input range: 15 -> 169  = 154 = 180 degree
    }


}
