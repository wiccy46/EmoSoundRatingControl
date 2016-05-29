package com.jiajunyang.emosoundratingcontrol;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;


/**
 * Created by jiajunyang on 27/05/16.
 */


public class Test extends Activity {
    private String myIP = "192.168.0.2";
    private int myPort = 50010;
    private String action;

    public void onPlayClick(View view){
        action = "play";
        Thread play = new Thread(new OSCSend(myIP, myPort, action));
        play.start();
    }

    public void onNextClick(View view){
        action = "next";
        Thread play = new Thread(new OSCSend(myIP, myPort, action));
        play.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test); // Include the correspondent xml filename.
        }

    }
