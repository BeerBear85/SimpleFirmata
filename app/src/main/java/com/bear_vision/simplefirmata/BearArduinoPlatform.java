package com.bear_vision.simplefirmata;

/**
 * Created by BJES on 06-03-2018.
 */
import android.app.Activity;

import org.shokai.firmata.*;

import java.io.IOException;

public class BearArduinoPlatform {

    private ArduinoFirmata mArduino;
    public ServoControlClass mServo;
    public LEDControlClass mLED1;
    public LEDControlClass mLED2;

    private Thread mServoThread;
    private Thread mLED1Thread;
    private Thread mLED2Thread;

    //Pin setup
    private int mServoPin = 3;
    private int mLedPin1 = 13;
    private int mLedPin2 = 11;

    public BearArduinoPlatform(Activity arg_activity)
    {
        mArduino = new ArduinoFirmata(arg_activity);

        try{
            mArduino.connect();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }

        mServo = new ServoControlClass(mArduino, mServoPin);
        mServoThread = new Thread(mServo);
        mServoThread.start();

        mLED1 = new LEDControlClass(mArduino, mLedPin1);
        mLED1Thread = new Thread(mLED1);
        mLED1Thread.start();

        mLED2 = new LEDControlClass(mArduino, mLedPin2);
        mLED2Thread = new Thread(mLED2);
        mLED2Thread.start();
    }

}
